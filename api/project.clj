(defproject api "0.1.0"
  :description "Open APIs for Paradigm X micro-services"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.logging "0.4.0"]
                 [metosin/compojure-api "1.2.0-alpha8"]
                 [ring-cors "0.1.10"]
                 [clj-time "0.13.0"]]
  :ring {:handler api.handler/app}
  :uberjar-name "server.jar"
  :profiles {:dev {:dependencies [[javax.servlet/javax.servlet-api "3.1.0"]]
                   :plugins [[lein-ring "0.12.0"]]}})
