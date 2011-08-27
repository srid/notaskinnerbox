(ns notaskinnerbox.views
  (:require [notaskinnerbox.stackexchange :as sx])
  (:use [hiccup core page-helpers])
  (:import [java.text SimpleDateFormat]))


(defn- item-url [item site]
  (str "http://" site "/questions/"
       (:question_id item)))


(defn format-date
  [date]
  (.format (SimpleDateFormat.  "yyyy-MM-dd") date))


(defn- page-title [site tag n]
  (str "weekly " site
       (if (not (= tag "")) (str " [" tag "] "))
       (if (> n 0) (str " (" n " days)"))))


(defn index-page [site tag n]
  (html5
    [:head
     [:meta {:charset "UTF8"}]
     [:title (page-title site tag n)]
     (include-css "/css/lessframework.css")
     (include-css "http://fonts.googleapis.com/css?family=Cantarell:400,700")
     (include-css "/css/style.css")]
    [:body
     [:header
      [:hgroup
       [:h1 "not a skinners box"]
       [:h3 (page-title site tag n)]]]
     [:section
      (for [item (sx/top-posts site tag n)]
        [:article
         [:span (:score item)]
         " "
         (let [date (format-date (:creation_date item))]
           [:time {:datetime date :pubDate "pubDate"} date])
         " "
         [:a {:href (item-url item site)} (:title item)]])]
     [:footer
      [:nav
       [:h1 "Navigation"]
       [:ul
        [:li [:a {:href "/"} "Home"]]]]]]))



