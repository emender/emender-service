(ns emender-service.server)

(require '[ring.util.response     :as http-response])

(defn handler
    "Handler that is called by Ring for all requests received from user(s)."
    [request]
    (println "request URI: " (request :uri))
    )

