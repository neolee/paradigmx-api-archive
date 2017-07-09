(ns paradigmx.handler
  (:require [clojure.tools.logging :as log]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [ring.middleware.cors :refer [wrap-cors]]
            [paradigmx.holiday :refer [holiday? holidays-in-month]]
            [paradigmx.spell :refer [correct]]))

(def app
  (->
   (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "Paradigm X"
                    :description "Open APIs for Paradigm X micro-services"}
             :tags [{:name "holiday", :description "Holiday data services"}
                    {:name "spell", :description "Spell checker and corrector"}]}}}
    (context "/holiday" []
      :tags ["holiday"]
      (GET "/check" []
        :return {:isHoliday Boolean}
        :query-params [date :- String]
        :summary "test given date return true if it's a holiday"
        (ok {:isHoliday (holiday? date)}))
      (GET "/holidays" []
        :return {:holidays [String]}
        :query-params [month :- String]
        :summary "return all holidays in given month"
        (ok {:holidays (holidays-in-month month)})))
    (context "/spell" []
      :tags ["spell"]
      (GET "/correct" []
        :return {:word String}
        :query-params [word :- String]
        :summary "return best guess on correcting input word"
        (ok {:word (correct word)}))))
   (wrap-cors :access-control-allow-origin #".*"
              :access-control-allow-methods [:get :put :post]
              :access-control-allow-headers ["Content-Type"])))
