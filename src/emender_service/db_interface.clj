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

(ns emender-service.db-interface
    "Namespace that contains configuration of all JDBC sources.")

(require '[clojure.java.jdbc       :as jdbc])

(require '[emender-service.db-spec :as db-spec])



(def date-format
    (new java.text.SimpleDateFormat "yyyy-MM-dd HH:mm:ss"))

(defn format-date
    [date]
    (.format date-format date))

(defn format-current-date
    []
    (format-date (new java.util.Date)))

(defn record-request-log
    [uri ipaddress datetime params useragent]
    (try
        (jdbc/insert! db-spec/emender-service-db
                      :requests {:uri uri :ipaddress ipaddress :datetime datetime :params params :useragent useragent})
        (catch Exception e
            (println e))))

(defn record-job-event
    [job-name datetime operation]
    (try
        (jdbc/insert! db-spec/emender-service-db
                      :jobs {:job job-name :datetime datetime :operation operation})
        (catch Exception e
            (println e))))

(defn record-job-results
    [job-name datetime results]
    (try
        (jdbc/insert! db-spec/emender-service-db
                      :results {:job job-name :datetime datetime :results results})
        (catch Exception e
            (println e))))

