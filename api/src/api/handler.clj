(ns api.handler
  (:require [clojure.tools.logging :as log]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [clj-time.core :as t]
            [clj-time.format :as f]
            [clj-time.predicates :as pr]
            ))

(def holidays {"2017" #{"0101", "0102"
                        "0127", "0128", "0129", "0130", "0131", "0201", "0202",
                        "0402", "0403", "0404",
                        "0501",
                        "0528", "0529", "0530",
                        "1001", "1002", "1003", "1004", "1005", "1006", "1007", "1008"}})

(def rotations {"2017" #{"0122", "0204",
                         "0401",
                         "0527",
                         "0930"}})

(defn rotation?
  "test given date return true if it's a rotation work day"
  [d]
  (let [y (subs d 0 4) md (subs d 4 8)]
    (if (contains? (rotations y) md)
      true
      false))
  )

(defn weekend?
  "test given date return true if it's a weekend day"
  [d]
  (pr/weekend? (f/parse (f/formatter "yyyyMMdd") d))
  )

(defn holiday?
  "test given date return true if it's a holiday"
  [d]
  (let [y (subs d 0 4) md (subs d 4 8)]
    (if (contains? (holidays y) md)
      true
      (if (and (weekend? d) (not (rotation? d)))
        true
        false)))
  )

(def app
  (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "Paradigm X"
                    :description "Open APIs for Paradigm X micro-services"}
             :tags [{:name "util", :description "Utilities"}
                    {:name "holiday", :description "Holiday data services"}]}}}

    (context "/util" []
      :tags ["util"]

      (GET "/plus" []
        :return {:result Long}
        :query-params [x :- Long, y :- Long]
        :summary "adds two numbers together"
        (ok {:result (+ x y)}))
      )

    (context "/holiday" []
      :tags ["holiday"]

      (GET "/check" []
        :return {:isHoliday Boolean}
        :query-params [d :- String]
        :summary "test given date return true if it's a holiday"
        (ok {:isHoliday (holiday? d)}))
      )
    ))
