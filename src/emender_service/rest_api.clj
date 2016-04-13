;
;  (C) Copyright 2016  Pavel Tisnovsky
;
;  All rights reserved. This program and the accompanying materials
;  are made available under the terms of the Eclipse Public License v1.0
;  which accompanies this distribution, and is available at
;  http://www.eclipse.org/legal/epl-v10.html
;
;  Contributors:
;      Pavel Tisnovsky
;

(ns emender-service.rest-api)

(require '[ring.util.response           :as http-response])
(require '[clojure.pprint               :as pprint])
(require '[clojure.data.json            :as json])

(require '[emender-service.file-utils   :as file-utils])
(require '[emender-service.results      :as results])
(require '[emender-service.db-interface :as db-interface])
(require '[emender-service.git-utils    :as git-utils])
(require '[emender-service.config       :as config])
(require '[emender-service.exec         :as exec])

(defn read-request-body
    [request]
    (file-utils/slurp- (:body request)))

(defn body->results
    [body]
    (json/read-str body))

(defn body->test-info
    [body]
    (json/read-str body :key-fn clojure.core/keyword))

(defn read-job-name-from-uri
    [uri]
    (-> uri
        (subs (inc (.lastIndexOf uri "/")))
        (clojure.string/replace "+" " ")))

(defn read-job-name-from-request
    [request]
    (read-job-name-from-uri (:uri request)))

(defn send-response
    [response]
    (-> (http-response/response (json/write-str response))
        (http-response/content-type "application/json")))

(defn info-handler
    [request hostname]
    (let [response {:toasterNotifications [(str "info|Api response|<strong>Emender Service</strong> api v1 on</br>" hostname)]
                    :configuration (:configuration request)}]
        (send-response response)))

(defn unknown-call-handler
    [uri method]
    (db-interface/log-error "Unknown API call" (str "URI: " uri "  method: " method))
    (let [response {:status :error
                    :error "Unknown API call"
                    :uri uri
                    :method method}]
        (send-response response)))

(defn job-started-handler
    [request]
    (let [job-name (read-job-name-from-request request)]
        (db-interface/log-job-started job-name)
        (println "job started" job-name)
        (send-response {:status :ok})))

(defn job-finished-handler
    [request]
    (let [job-name (read-job-name-from-request request)]
        (db-interface/log-job-finished job-name)
        (println "job finished" job-name)
        (send-response {:status :ok})))

(defn job-results-handler
    [request]
    (let [job-name (read-job-name-from-request request)
          results  (-> (read-request-body request) body->results)]
        (db-interface/log-job-results job-name results)
        (println "job name" job-name)
        (println "results:" results)
        (results/add-new-results job-name results)
        ;(pprint/pprint results)
        (send-response {:status :ok})))

(defn get-job-list-handler
    [request]
    (let [res @results/results
          names (keys res)]
          (if names
              (send-response names)
              (send-response []))))

(defn repo-clone-failed
    [clone-results job-name repo-url branch]
    (let [message (str "Problem cloning repository '" repo-url "' and/or checkout branch '" branch "'")]
        (println message)
        (db-interface/log-error job-name repo-url branch message (:message clone-results)))
    (send-response {:status :repo-clone-failed}))

(defn create-tests+paths
    [path-to-tests tests]
    (map #(str path-to-tests "/" % ".lua") tests))

(defn run-emend
    [path-to-emender path-to-tests tests book-directory]
    (println "Starting Emend against the book directory" book-directory)
    (let [tests+paths (create-tests+paths path-to-tests tests)]
        (apply exec/exec "scripts/start_proceed" path-to-emender path-to-tests book-directory tests+paths))
    (send-response {:status :ok}))

(defn run-test-handler
    [request]
    (let [job-name      (read-job-name-from-request request)
          path-to-emend (file-utils/>abs-path (config/path-to-emender request))
          path-to-tests (file-utils/>abs-path (config/path-to-tests request))
          test-info     (-> (read-request-body request) body->test-info)
          repo-url      (-> test-info :repository :url)
          branch        (-> test-info :repository :branch)
          tests         (-> test-info :tests)]
        (println "job name " job-name)
        (println "test dir " path-to-tests)
        (println "repo URL " repo-url)
        (println "branch   " branch)
        (println "Emender  " path-to-emend)
        (println "tests    " tests)
        (let [;clone-results {:directory (new java.io.File "/tmp/emender-service-1460557400756/")}]
              clone-results (git-utils/clone-remote-repository repo-url branch)]
             (if (:directory clone-results)
                 (run-emend path-to-emend path-to-tests tests
                            (.getAbsolutePath (:directory clone-results)))
                 (repo-clone-failed (:message clone-results) job-name repo-url branch)))))

