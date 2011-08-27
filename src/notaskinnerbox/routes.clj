(ns notaskinnerbox.routes
  (:use compojure.core
        notaskinnerbox.views
        ring.adapter.jetty
        ring.middleware.stacktrace
        [hiccup.middleware :only (wrap-base-url)])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]
            [notaskinnerbox.stackexchange :as sx]))


(defroutes main-routes
  (GET "/" [] (index-page "stackoverflow.com" "" 7))
  (GET ["/se/:site", :site #"[a-z\.]+"] [site]
       (index-page site "" 7))
  (GET ["/se/:site/:tag", :site #"[a-z\.]+"] [site tag]
       (index-page site tag 7))
  (GET ["/se/:site/:tag/:n", :site #"[a-z\.]+"] [site tag n]
       (index-page site tag (. Integer parseInt n)))

  (route/resources "/")
  (route/not-found "Page not found"))


(def app
  (-> (handler/site main-routes)
      (wrap-stacktrace)
      (wrap-base-url)))


;; for heroku/stackato
(defn -main []
  (let [port (Integer/parseInt
              (get (System/getenv) "VCAP_APP_PORT"
                   (get (System/getenv) "PORT" "8080")))]
    (println (str "Starting at " port))
    (run-jetty app {:port port})))
