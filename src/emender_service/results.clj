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

(ns emender-service.results)

(require '[emender-service.file-utils :as file-utils])

(def results (atom {}))

(defn render-edn-data
    "Render EDN data to be used as off-memory cache."
    [output-data]
    (with-out-str (clojure.pprint/pprint output-data)))

(defn add-new-results
    [job-name new-results]
    (swap! results assoc job-name new-results))

(defn store-results
    []
    (let [edn-data (render-edn-data @results)]
        (spit "results2.edn" edn-data)
        ; rename files atomically (on the same filesystem)
        (file-utils/mv-file "results2.edn" "results.edn")))

