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
(require '[emender-service.db-interface  :as db-interface])

(defn render-front-page
    "Create front page."
    [request]
    (-> (http-response/response (html-renderer/render-front-page))
        (http-response/content-type "text/html")))

(defn render-job-list-page
    [request]
    (let [job-list (db-interface/read-job-list)]
        (println job-list)
        (-> (http-response/response (html-renderer/render-job-list-page job-list))
            (http-response/content-type "text/html"))))

(defn render-book-list-page
    [request]
    (let [book-list (db-interface/read-book-list)]
        (println book-list)
        (-> (http-response/response (html-renderer/render-book-list-page book-list))
            (http-response/content-type "text/html"))))

(defn render-jobs-for-book
    [request]
    (let [book-name (-> request :params (get "book"))
          job-list  (db-interface/read-job-list book-name)]
        (println job-list)
        (-> (http-response/response (html-renderer/render-job-list-page job-list))
            (http-response/content-type "text/html"))))

(defn render-job-info
    [request]
    (let [job-id (-> request :params (get "id"))
          job-info (db-interface/read-job-info job-id)
          test-results (json/read-str (:results job-info))]
        (-> (http-response/response (html-renderer/render-job-info job-info test-results))
            (http-response/content-type "text/html"))))

(defn render-operation-log
    [request]
    (let [log (db-interface/read-operation-log)]
        (println log)
        (-> (http-response/response (html-renderer/render-operation-log-page log))
            (http-response/content-type "text/html"))))

(defn render-request-log
    [request]
    (let [log (db-interface/read-requests-log)]
        (println log)
        (-> (http-response/response (html-renderer/render-request-log-page log))
            (http-response/content-type "text/html"))))

(defn render-results
    [request]
    (let [results (db-interface/read-results)]
        (println results)
        (-> (http-response/response (html-renderer/render-results-page results))
            (http-response/content-type "text/html"))))

(defn render-errors
    [request]
    (let [log (db-interface/read-error-log)]
        (println log)
        (-> (http-response/response (html-renderer/render-error-log-page log))
            (http-response/content-type "text/html"))))

(defn render-log
    [request]
    (let [log (db-interface/read-log)]
        (println log)
        (-> (http-response/response (html-renderer/render-log-page log))
            (http-response/content-type "text/html"))))

(defn render-configuration
    [request]
    (let [configuration (:configuration request)]
        (println configuration)
        (-> (http-response/response (html-renderer/render-configuration-page configuration))
            (http-response/content-type "text/html"))))

(defn render-test-results
    [request]
    (let [job-id (-> request :params (get "job"))]
        (if job-id
            (let [output-html (db-interface/read-html-results-for-job-id job-id)]
                (-> (http-response/response output-html)
            (http-response/content-type "text/html"))))))

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
        [:get  "/job-list"]     (rest-api/get-job-list-handler request)
        [:get  "/jobs"]         (rest-api/get-job-list-handler request)
        [:get  "/book-list"]    (rest-api/get-book-list-handler request)
        [:get  "/books"]        (rest-api/get-book-list-handler request)
        [:get  "/job-info"]     (rest-api/get-job-info-handler request)
        [:post "/job-started"]  (rest-api/job-started-handler  request)
        [:post "/job-finished"] (rest-api/job-finished-handler request)
        [:post "/job-results"]  (rest-api/job-results-handler  request)
        [:post "/run-test"]     (rest-api/run-test-handler     request)
                                (rest-api/unknown-call-handler uri method)))

(defn non-api-call-handler
    [request uri]
    (condp = uri
        "/favicon.ico"                     (return-file "favicon.ico"         "image/x-icon")
        "/emender-service.css"             (return-file "emender-service.css" "image/x-icon")
        "/bootstrap.min.css"               (return-file "bootstrap.min.css"   "text/css")
        "/emender-service.css"             (return-file "emender-service.css" "text/css")
        "/bootstrap.min.js"                (return-file "bootstrap.min.js"    "application/javascript")
        "/"                      (render-front-page     request)
        "/job-list"              (render-job-list-page  request)
        "/book-list"             (render-book-list-page request)
        "/jobs-for-book"         (render-jobs-for-book  request)
        "/job-info"              (render-job-info       request)
        "/operation-log"         (render-operation-log  request)
        "/request-log"           (render-request-log    request)
        "/results"               (render-results        request)
        "/errors"                (render-errors         request)
        "/log"                   (render-log            request)
        "/configuration"         (render-configuration  request)
        "/rendered-test-results" (render-test-results   request)))

(defn handler
    "Handler that is called by Ring for all requests received from user(s)."
    [request]
    (db-interface/log-request-information request)
    (if (config/verbose? request)
        (pprint/pprint request))
    (let [uri    (:uri request)
          method (:request-method request)]
         (if (.startsWith uri (config/get-api-prefix request))
             (api-call-handler request uri method)
             (non-api-call-handler request uri))))

