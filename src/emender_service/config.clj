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

(ns emender-service.config)

(require '[clojure.pprint :as pprint])

(require '[clojure-ini.core :as clojure-ini])

(defn parse-int
    "Parse the given string as an integer number."
    [string]
    (java.lang.Integer/parseInt string))

(defn parse-float
    "Parse the given string as a float number."
    [string]
    (java.lang.Float/parseFloat string))

(defn parse-boolean
    "Parse the given string as a boolean value."
    [string]
    (or (= string "true")
        (= string "True")))

(defn update-configuration
    "Update selected items in the configuration structure."
    [configuration]
    (-> configuration
        (update-in [:jenkins :port] parse-int)
        (update-in [:result-cache :pretty-print] parse-boolean)
        (update-in [:result-cache :use-cache] parse-boolean)
        (update-in [:config :verbose] parse-boolean)
        (update-in [:config :delete-workdirs] parse-boolean)))

(defn load-configuration
    "Load configuration from the provided INI file."
    [ini-file-name]
    (-> (clojure-ini/read-ini ini-file-name :keywordize? true)
        update-configuration))

(defn print-configuration
    "Print actual configuration to the output."
    [configuration]
    (pprint/pprint configuration))

(defn get-api-prefix
    [request]
    (-> request :configuration :api :prefix))

(defn path-to-emender
    [request]
    (-> request :configuration :emender :path))

(defn path-to-tests
    [request]
    (-> request :configuration :emender :tests))

(defn verbose?
    [request]
    (-> request :configuration :config :verbose))

(defn use-result-cache?
    [request]
    (-> request :configuration :result-cache :use-cache))

(defn pretty-print-edn?
    [request]
    (-> request :configuration :result-cache :pretty-print))

(defn delete-workdirs?
    [request]
    (-> request :configuration :config :delete-workdirs))

