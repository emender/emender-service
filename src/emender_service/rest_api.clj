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

(defn info-handler
    [request hostname]
    (let [response {:toasterNotifications [(str "info|Api response|<strong>Emender Service</strong> api v1 on</br>" hostname)]
                    :configuration (:configuration request)}]
        (-> (http-response/response (json/write-str response))
            (http-response/content-type "application/json"))))

(defn unknown-call-handler
    [uri method]
    (let [response {:error "Unknown API call"
                    :uri uri
                    :method method}]
        (-> (http-response/response (json/write-str response))
            (http-response/content-type "application/json"))))

