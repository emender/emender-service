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

(require '[ring.util.response     :as http-response])
(require '[clojure.data.json      :as json])

(require '[emender-service.file-utils :as file-utils])

(defn read-request-body
    [request]
    (file-utils/slurp- (:body request)))

(defn body->job-name
    [body]
    (let [data (json/read-str body)]
        (get data "name")))

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
    (let [job-name (-> (read-request-body request) body->job-name)]
        (println "job started" job-name)
        (send-response {:status :ok})))

(defn job-finished-handler
    [request]
    (let [job-name (-> (read-request-body request) body->job-name)]
        (println "job finished" job-name)
        (send-response {:status :ok})))

