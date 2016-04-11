(defproject emender-service "0.1.0-SNAPSHOT"
    :description "FIXME: write description"
    :url "http://example.com/FIXME"
    :license {:name "Eclipse Public License"
              :url "http://www.eclipse.org/legal/epl-v10.html"}
    :dependencies [[org.clojure/clojure "1.7.0"]
                   [org.clojure/java.jdbc "0.3.5"]
                   [org.clojure/tools.cli "0.3.1"]
                   [org.xerial/sqlite-jdbc "3.7.2"]
                   [clojure-ini "0.0.1"]
                   [ring/ring-core "1.3.2"]
                   [ring/ring-jetty-adapter "1.3.2"]
                   [org.clojure/data.json "0.2.5"]
                   [clj-http "2.0.0"]
                   [clj-jgit "0.8.0"]
                   [hiccup "1.0.4"]
                   [irclj "0.5.0-alpha4"]]
    :dev-dependencies [[lein-ring "0.8.10"]]
    :plugins [[lein-ring "0.8.10"]
              [codox "0.8.11"]
              [test2junit "1.1.0"]
              [lein-cloverage "1.0.6"]]
    :main ^:skip-aot emender-service.core
    :ring {:handler emender-service.core/app}
    :profiles {:uberjar {:aot :all}})

