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
    "Namespace that contains interface to the database.")

(require '[clojure.java.jdbc       :as jdbc])

(require '[emender-service.db-spec     :as db-spec])
(require '[emender-service.format-date :as format-date])

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
    [job-name repo-url branch datetime results]
    (try
        (jdbc/insert! db-spec/emender-service-db
                      :results {:job job-name :datetime datetime :url repo-url :branch branch :results results})
        (catch Exception e
            (println e))))

(defn record-error
    [datetime message stacktrace]
    (try
        (jdbc/insert! db-spec/emender-service-db
                      :errors {:datetime datetime :message message :stacktrace stacktrace})
        (catch Exception e
            (println e))))

(defn log-request-information
    [request]
    (let [uri       (:uri request)
          ipaddress (:remote-addr request)
          datetime  (format-date/format-current-date)
          params    (:params request)
          useragent ((:headers request) "user-agent")]
          (record-request-log uri ipaddress datetime params useragent)))

(defn log-job-started
    [job-name]
    (record-job-event job-name (format-date/format-current-date) "started"))

(defn log-job-finished
    [job-name]
    (record-job-event job-name (format-date/format-current-date) "finished"))

(defn log-job-results
    ([job-name repo-url branch results]
     (let [formatted-date (format-date/format-current-date)]
         (record-job-event job-name formatted-date "results")
         (record-job-results job-name repo-url branch formatted-date results)))
    ([job-name results]
     (log-job-results job-name nil nil results)))

(defn log-error
    [message stacktrace]
    (record-error (format-date/format-current-date message stacktrace)))

