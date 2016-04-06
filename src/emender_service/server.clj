(ns emender-service.server)

(require '[ring.util.response     :as http-response])

(require '[emender-service.html-renderer :as html-renderer])

(defn render-front-page
    "Create front page."
    [request]
    (-> (http-response/response (html-renderer/render-front-page))
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
            "/favicon.ico"         (return-file "favicon.ico"         "image/x-icon")
            "/emender-service.css" (return-file "emender-service.css" "image/x-icon")
            "/bootstrap.min.css"   (return-file "bootstrap.min.css"   "text/css")
            "/emender-service.css" (return-file "emender-service.css" "text/css")
            "/bootstrap.min.js"    (return-file "bootstrap.min.js"    "application/javascript")
            "/"                    (render-front-page request)
    )))

