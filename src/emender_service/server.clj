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
(require '[clojure.pprint         :as pprint])
(require '[clojure.data.json      :as json])

(require '[emender-service.html-renderer :as html-renderer])
(require '[emender-service.results       :as results])
(require '[emender-service.config        :as config])
(require '[emender-service.rest-api      :as rest-api])

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

(defn get-hostname
    []
    (.. java.net.InetAddress getLocalHost getHostName))

(defn get-api-command
    [request uri]
    (if uri
        (re-find #"/[^/]*" (subs uri (count (config/get-api-prefix request))))))

(defn api-call-handler
    [request uri method]
    (condp = [method (get-api-command request uri)]
        [:get  "/info"]         (rest-api/info-handler request (get-hostname))
        [:post "/job-started"]  (rest-api/job-started-handler  request)
        [:post "/job-finished"] (rest-api/job-finished-handler request)
        [:post "/job-results"]  (rest-api/job-results-handler  request)
                                (rest-api/unknown-call-handler uri method)))

(defn non-api-call-handler
    [request uri]
    (condp = uri
        "/favicon.ico"         (return-file "favicon.ico"         "image/x-icon")
        "/emender-service.css" (return-file "emender-service.css" "image/x-icon")
        "/bootstrap.min.css"   (return-file "bootstrap.min.css"   "text/css")
        "/emender-service.css" (return-file "emender-service.css" "text/css")
        "/bootstrap.min.js"    (return-file "bootstrap.min.js"    "application/javascript")
        "/"                    (render-front-page request)))

(defn handler
    "Handler that is called by Ring for all requests received from user(s)."
    [request]
    (if (config/verbose? request)
        (pprint/pprint request))
    (let [uri    (:uri request)
          method (:request-method request)]
         (if (.startsWith uri (config/get-api-prefix request))
             (api-call-handler request uri method)
             (non-api-call-handler request uri))))

