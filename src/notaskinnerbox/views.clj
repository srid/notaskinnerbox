(ns notaskinnerbox.views
  (:use [hiccup core page-helpers]))

(defn index-page []
  (html5
    [:head
      [:title "Hello World"]
      (include-css "/css/style.css")]
    [:body
      [:h1 "Welcome to notaskinnerbox, using Compojure and Hiccup"]]))

