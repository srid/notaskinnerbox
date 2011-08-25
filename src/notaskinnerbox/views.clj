(ns notaskinnerbox.views
  (:require [notaskinnerbox.stackexchange :as sx])
  (:use [hiccup core page-helpers]))


(defn- item-url [item site]
  (str "http://" site "/questions/"
       (:question_id item)))


(defn- page-title [site tag n]
  (str "weekly " site
       (if (not (= tag "")) (str " [" tag "] "))
       (if (> n 0) (str " (" n " days)"))))


(defn index-page [site tag n]
  (html5
    [:head
     [:title (page-title site tag n)]
     (include-css "/css/lessframework.css")
     (include-css "http://fonts.googleapis.com/css?family=Cantarell:400,700")
     (include-css "/css/style.css")]
    [:body
     [:h1 (page-title site tag n)]
     (for [item (sx/top-posts site tag n)]
       [:li
        [:span (:score item)]
        " "
        [:a {:href (item-url item site)} (:title item)]])]))



