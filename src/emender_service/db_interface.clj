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

(defn record-job-operation-event
    [job-name repo-url branch datetime operation]
    (try
        (jdbc/insert! db-spec/emender-service-db
                      :job_operations {:job job-name :url repo-url :branch branch :datetime datetime :operation operation})
        (catch Exception e
            (println e))))

(defn record-job-results
    [job-name repo-url branch datetime results]
    (try
        (jdbc/insert! db-spec/emender-service-db
                      :results {:job job-name :datetime datetime :url repo-url :branch branch :results results})
        (catch Exception e
            (println e))))

(defn read-job-list
    ([]
    (try
        (jdbc/query db-spec/emender-service-db
                    ["select id, job from results order by id,job"])
        (catch Exception e
            (println e)
            [])))
    ([book-name]
    (try
        (jdbc/query db-spec/emender-service-db
                    ["select id, job from results where job=? order by id,job" book-name])
        (catch Exception e
            (println e)
            []))))

(defn read-book-list
    []
    (try
        (jdbc/query db-spec/emender-service-db
                    ["select distinct job from results"])
        (catch Exception e
            (println e)
            [])))

(defn read-job-info
    [job-id]
    (try
        (first
            (jdbc/query db-spec/emender-service-db
                        ["select * from results where id = ?" job-id]))
        (catch Exception e
            (println e)
            [])))

(defn read-operation-log
    []
    (try
        (jdbc/query db-spec/emender-service-db
                    ["select * from job_operations order by id"])
        (catch Exception e
            (println e)
            [])))

(defn read-log
    []
    (try
        (jdbc/query db-spec/emender-service-db
                    ["select * from log order by id"])
        (catch Exception e
            (println e)
            [])))

(defn read-error-log
    []
    (try
        (jdbc/query db-spec/emender-service-db
                    ["select * from errors order by id"])
        (catch Exception e
            (println e)
            [])))

(defn read-requests-log
    []
    (try
        (jdbc/query db-spec/emender-service-db
                    ["select * from requests order by id"])
        (catch Exception e
            (println e)
            [])))

(defn read-results
    []
    (try
        (jdbc/query db-spec/emender-service-db
                    ["select id, datetime, job, url, branch from results order by id"])
        (catch Exception e
            (println e)
            [])))

(defn record-error
    [job-name repo-url branch datetime message stacktrace]
    (try
        (jdbc/insert! db-spec/emender-service-db
                      :errors {:job job-name :url repo-url :branch branch :datetime datetime :message message :stacktrace stacktrace})
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
    ([job-name repo-url branch]
     (record-job-operation-event job-name repo-url branch (format-date/format-current-date) "started"))
    ([job-name]
     (log-job-started job-name nil nil)))

(defn log-job-finished
    ([job-name repo-url branch]
     (record-job-operation-event job-name repo-url branch (format-date/format-current-date) "finished"))
    ([job-name]
     (log-job-finished job-name nil nil)))

(defn log-job-results
    ([job-name repo-url branch results]
     (let [formatted-date (format-date/format-current-date)]
         (record-job-operation-event job-name repo-url branch formatted-date "results")
         (record-job-results job-name repo-url branch formatted-date results)))
    ([job-name results]
     (log-job-results job-name nil nil results)))

(defn log-error
    ([job-name repo-url branch message stacktrace]
     (record-error job-name repo-url branch (format-date/format-current-date) message stacktrace))
    ([message stacktrace]
     (log-error nil nil nil message stacktrace)))

