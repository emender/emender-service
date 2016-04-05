(defproject emender-service "0.1.0-SNAPSHOT"
    :description "FIXME: write description"
    :url "http://example.com/FIXME"
    :license {:name "Eclipse Public License"
              :url "http://www.eclipse.org/legal/epl-v10.html"}
    :dependencies [[org.clojure/clojure "1.6.0"]
                   [org.clojure/tools.cli "0.3.1"]
                   [ring/ring-core "1.3.2"]
                   [ring/ring-jetty-adapter "1.3.2"]
                   [hiccup "1.0.4"]]
    :dev-dependencies [[lein-ring "0.8.10"]]
    :main ^:skip-aot emender-service.core
    :ring {:handler emender-service.core/app}
    :profiles {:uberjar {:aot :all}})
