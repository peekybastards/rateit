(ns rateit.core
  (:require [ring.adapter.jetty :as jetty] [reitit.ring :as reitit] [cheshire.core :as json] [ring.middleware.json :refer [wrap-json-response wrap-json-body]] [ring.util.response :refer [response]])
  (:gen-class))

(defn ping-handler [_] 
  (response {:success true 
              :error false 
              :message "sent you a ping masta"}))



(defn post-handler [request] 
  (let [data (:body request)]
    (response (json/generate-string data))))





(def routes (reitit/router ["/api" 
                              ["/ping" {:handler ping-handler :middleware [wrap-json-response]}]
                              ["/posty" {:handler post-handler :middleware [[wrap-json-body] [wrap-json-response]]}]
                            ]))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (jetty/run-jetty (reitit/ring-handler routes) {:join? false :port 8091})
  (println "Hello, World!"))
