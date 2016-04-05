(ns emender-service.server)

(require '[ring.util.response     :as http-response])

(defn render-front-page
    "Create front page."
    [request]
    (-> (http-response/response "<html><body>xyzzy</body></html>")
        (http-response/content-type "text/html")))

(defn return-file
    "Creates HTTP response containing content of specified file.
     Special value nil / HTTP response 404 is returned in case of any I/O error."
    [file-name content-type]
    (let [file (new java.io.File "www" file-name)]
        (println "Returning file " (.getAbsolutePath file))
        (if (.exists file)
            (-> (http-response/response file)
                (http-response/content-type content-type))
            (println "return-file(): can not access file: " (.getName file)))))

(defn handler
    "Handler that is called by Ring for all requests received from user(s)."
    [request]
    (println "request URI: " (request :uri))
    (let [uri (request :uri)]
        (condp = uri
            "/favicon.ico"       (return-file "favicon.ico" "image/x-icon")
            "/"                  (render-front-page request)
    )))

