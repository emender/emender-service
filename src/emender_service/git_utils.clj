(ns emender-service.git-utils)

(require '[clj-jgit.porcelain         :as jgit])
(require '[emender-service.file-utils :as file-utils])

(def home-directory
    (System/getProperty "user.home"))

(defn slurp-file-from-homedir-
    [file-name]
    (slurp (str home-directory "/" file-name)))

(defn get-origin-for-git-directory
    [git-directory]
    (try
        (jgit/with-repo (.getAbsolutePath git-directory)
            (-> repo
                (.getRepository)
                (.getConfig)
                (.getString "remote" "origin" "url")))
        (catch Exception e
            (println "*** Exception *** " e)
            {:error (.getMessage e)})))

(defn clone-repository
    "Clone repository into specified directory."
    [url directory]
    (try
        (binding [jgit/*ssh-prvkey* (slurp-file-from-homedir- ".ssh/id_rsa")
                  jgit/*ssh-pubkey* (slurp-file-from-homedir- ".ssh/id_rsa.pub")]
            (jgit/git-clone url directory))
        (catch Exception e
            (println "*** Exception *** " e)
            {:error (.getMessage e)})))

(defn checkout-branch
    [repo branch-name]
    (try
        (jgit/git-checkout repo branch-name)
        true
        (catch Exception e
            (println "*** Exception *** " e)
            nil)))

(defn checkout-origin-branch
    [repo branch-name]
    (checkout-branch repo (str "origin/" branch-name)))

(defn clone-remote-repository
    [repository-url branch]
    (println repository-url)
    (println branch)
    (let [temporary-repodir (file-utils/make-temporary-directory)
          clone-repo-result (clone-repository repository-url temporary-repodir)]
          (if (:error clone-repo-result)
              (do
                  ;(file-utils/remove-directory temporary-repodir)
                  {:directory nil
                   :status "Failure"
                   :message (str "can not clone repository: " (:error clone-repo-result))})
              (do
                  (jgit/with-repo temporary-repodir
                      (if (not (checkout-origin-branch repo branch))
                          (do
                              ;(file-utils/remove-directory temporary-repodir)
                              {:directory nil
                               :status "Failure"
                               :message (str "unable to checkout branch '" branch "'")})
                          {:directory temporary-repodir
                           :status "OK"
                           :message "clone ok"})))
          )))

