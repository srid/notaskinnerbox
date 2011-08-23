(ns notaskinnerbox.routes
  (:use compojure.core
        notaskinnerbox.views
        [hiccup.middleware :only (wrap-base-url)])
  (:require [appengine-magic.core :as ae]
            [compojure.route :as route]
            [compojure.response :as response]
            [notaskinnerbox.stackexchange :as sx]))


(defroutes app-handler
  (GET "/" [] (index-page))
  (route/resources "/")
  (route/not-found "Page not found"))


(ae/def-appengine-app notaskinnerbox-app #'app-handler)

