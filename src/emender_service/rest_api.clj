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

(require '[ring.util.response         :as http-response])
(require '[clojure.pprint             :as pprint])
(require '[clojure.data.json          :as json])

(require '[emender-service.file-utils :as file-utils])
(require '[emender-service.results    :as results])

(defn read-request-body
    [request]
    (file-utils/slurp- (:body request)))

(defn body->results
    [body]
    (json/read-str body))

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
    (let [response {:status :error
                    :error "Unknown API call"
                    :uri uri
                    :method method}]
        (send-response response)))

(defn job-started-handler
    [request]
    (let [job-name (read-job-name-from-request request)]
        (println "job started" job-name)
        (send-response {:status :ok})))

(defn job-finished-handler
    [request]
    (let [job-name (read-job-name-from-request request)]
        (println "job finished" job-name)
        (send-response {:status :ok})))

(defn job-results-handler
    [request]
    (let [job-name (read-job-name-from-request request)
          results  (-> (read-request-body request) body->results)]
        (println "job name" job-name)
        (println "results:" results)
        (results/add-new-results job-name results)
        ;(pprint/pprint results)
        (send-response {:status :ok})))

