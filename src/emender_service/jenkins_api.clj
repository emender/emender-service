(ns emender-service.jenkins-api)

(require '[clojure.xml       :as xml])
(require '[clojure.zip       :as zip])

(require '[clojure.data.json :as json])
(require '[clj-http.client   :as http-client])

