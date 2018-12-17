(ns paradigmx.holiday
  (:require [clojure.tools.logging :as log]
            [clj-time.core :as t]
            [clj-time.format :as f]
            [clj-time.predicates :as pr]
            [clj-time.periodic :as p]))

(def holidays {"2017" #{"0101", "0102",
                        "0127", "0130", "0131", "0201", "0202",
                        "0402", "0403", "0404",
                        "0501",
                        "0528", "0529", "0530",
                        "1002", "1003", "1004", "1005", "1006"}
               "2018" #{"0101",
                        "0215", "0216", "0219", "0220", "0221",
                        "0405", "0406",
                        "0430", "0501",
                        "0618",
                        "0924",
                        "1001", "1002", "1003", "1004", "1005",
                        "1231"}
               "2019" #{"0101",
                        "0204", "0205", "0206", "0207", "0208",
                        "0405",
                        "0501",
                        "0607",
                        "0913",
                        "1001", "1002", "1003", "1004", "1007"}})

(def rotations {"2017" #{"0122", "0204",
                         "0401",
                         "0527",
                         "0930"}
                "2018" #{"0211", "0224",
                         "0408",
                         "0428",
                         "0929", "0930",
                         "1229"}
                "2019" #{"0202", "0203",
                         "0929", "1012"}})

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

(defn holidays-in-month
  "return all holidays in given month"
  [month]
  (let [m (f/parse (f/formatter "yyyyMM") month)
        d1 (t/first-day-of-the-month m)
        d2 (t/last-day-of-the-month m)
        n (+ (t/in-days (t/interval d1 d2)) 1)
        days (map #(f/unparse (f/formatter "yyyyMMdd") %) (take n (p/periodic-seq d1 (t/days 1))))
        ]
    (filter holiday? days)
    )
  )
