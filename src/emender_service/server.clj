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

(ns emender-service.server)

(require '[ring.util.response     :as http-response])

(require '[emender-service.html-renderer :as html-renderer])
(require '[emender-service.results       :as results])

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

(defn job-start-handler
    [uri]
    (println "job started" uri))

(defn job-finished-handler
    [uri]
    (println "job finished" uri))

(defn unknown-call-handler
    [uri]
    (println "unknown API call" uri))

; todo API call prefix -> config

(defn api-call-handler
    [uri method]
    (condp = (subs uri (.length "/v1"))
        "/job-start"    (job-start-handler    uri)
        "/job-finished" (job-finished-handler uri)
                           (unknown-call-handler uri)))

(defn non-api-call-handler
    [request uri]
    (condp = uri
        "/favicon.ico"         (return-file "favicon.ico"         "image/x-icon")
        "/emender-service.css" (return-file "emender-service.css" "image/x-icon")
        "/bootstrap.min.css"   (return-file "bootstrap.min.css"   "text/css")
        "/emender-service.css" (return-file "emender-service.css" "text/css")
        "/bootstrap.min.js"    (return-file "bootstrap.min.js"    "application/javascript")
        "/"                    (render-front-page request)))

; todo API call prefix -> config

(defn handler
    "Handler that is called by Ring for all requests received from user(s)."
    [request]
    (println "request URI: " (:uri request))
    (println (:configuration request))
    (let [uri    (:uri request)
          method (:request-method request)]
         (if (.startsWith uri "/v1")
             (api-call-handler uri method)
             (non-api-call-handler request uri))))

