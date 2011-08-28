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
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
     [:title (str title " - notaskinnerbox")]
     (include-css "/css/lessframework.css")
     (include-css "http://fonts.googleapis.com/css?family=PT+Sans+Caption")
     (include-css "/css/style.css")]
    [:body {:lang "en"}
     [:header
      [:hgroup
       [:h1 "not a skinner box"]
       [:h3 title]]]
     [:section body]
     [:footer "caffeinated play with clojure "
      [:a {:href "https://github.com/srid/notaskinnerbox"} "on github"]]]))
  

(defn index-page [site tag n]
  (base-page
   (page-title site tag n)
   (for [item (sx/top-posts site tag n)]
     (let [date (format-date (:creation_date item))]
       [:article
        [:a {:href (item-url item site)} (:title item)]
        [:span {:class "meta"}
         [:span " " [:b (:score item)] " votes; "]
         [:span [:time {:datetime date :pubDate "pubDate"} date]]]]))))
   