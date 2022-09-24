(defproject api "0.1.0"
  :description "Open APIs for Paradigm X micro-services"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.logging "0.5.0"]
                 [clj-time "0.15.2"]
                 [metosin/compojure-api "2.0.0-SNAPSHOT"]
                 [ring-cors "0.1.13"]]
  :ring {:handler paradigmx.handler/app}
  :uberjar-name "server.jar"
  :profiles {:dev {:dependencies [[javax.servlet/javax.servlet-api "4.0.1"]]
                   :plugins [[lein-ring "0.12.5"]]}}
  :resource-paths ["resources"])
