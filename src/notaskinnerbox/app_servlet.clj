(ns notaskinnerbox.app_servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use notaskinnerbox.routes)
  (:use [appengine-magic.servlet :only [make-servlet-service-method]])
  (:require [appengine-magic.core :as ae]))


(ae/def-appengine-app notaskinnerbox-app #'app-handler)


(defn -service [this request response]
  ((make-servlet-service-method notaskinnerbox-app) this request response))
