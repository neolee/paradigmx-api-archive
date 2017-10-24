(defproject api "0.1.0"
  :description "Open APIs for Paradigm X micro-services"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.logging "0.4.0"]
                 ; [clj-time "0.13.0"]
                 [clj-time "0.14.0"]
                 ; [metosin/compojure-api "1.2.0-alpha8"]
                 [metosin/compojure-api "2.0.0-alpha10"]
                 [ring-cors "0.1.11"]]
  :ring {:handler paradigmx.handler/app}
  :uberjar-name "server.jar"
  :profiles {:dev {:dependencies [[javax.servlet/javax.servlet-api "4.0.0"]]
                   :plugins [[lein-ring "0.12.1"]]}}
  :jvm-opts ["-Xmx1g"]
  :resource-paths ["resources"])
