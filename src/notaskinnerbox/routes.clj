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
  (GET "/" [] (view-digest "stackoverflow.com" "" 7))
  (GET ["/se/:site", :site #"[a-z\.]+"] [site]
       (view-digest site "" 7))
  (GET ["/se/:site/:n", :site #"[a-z\.]+"] [site tag n]
       (view-digest site "" (. Integer parseInt n)))
  (GET ["/se/:site/:n/:tag", :site #"[a-z\.]+"] [site tag n]
       (view-digest site tag (. Integer parseInt n)))

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
