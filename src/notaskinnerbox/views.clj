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


(defn base-page
  [title body]
  (html5
    [:head
     [:meta {:charset "UTF8"}]
     [:title (str title " - notaskinnersbox")]
     (include-css "/css/lessframework.css")
     (include-css "http://fonts.googleapis.com/css?family=Cantarell:400,700")
     (include-css "/css/style.css")]
    [:body
     [:header
      [:hgroup
       [:h1 "not a skinners box"]
       [:h3 title]]]
     [:section body]
     [:footer
      [:nav
       [:h1 "Navigation"]
       [:ul
        [:li [:a {:href "/"} "Home"]]]]]]))
  

(defn index-page [site tag n]
  (base-page
   (page-title site tag n)
   (for [item (sx/top-posts site tag n)]
     (let [date (format-date (:creation_date item))]
       [:article
        [:span (:score item)]
        " "
        [:time {:datetime date :pubDate "pubDate"} date]
        " "
        [:a {:href (item-url item site)} (:title item)]]))))
   