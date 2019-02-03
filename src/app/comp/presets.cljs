
(ns app.comp.presets
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.comp.space :refer [=<]]
            [respo.core :refer [defcomp <> action-> cursor-> list-> span div]]
            [app.config :as config]
            [inflow-popup.comp.popup :refer [comp-popup]]
            [feather.core :refer [comp-i]]
            [clojure.set :refer [difference]]))

(def builtin-presets #{:flex :font-code :font-fancy :font-normal :fullscreen :scroll})

(defcomp
 comp-preset
 (preset on-click)
 (div
  {:style {:padding "0 8px",
           :margin 4,
           :cursor :pointer,
           :background-color (hsl 200 80 60),
           :color :white,
           :border-radius "8px"},
   :on-click on-click}
  (<> (name preset))))

(defcomp
 comp-presets-picker
 (states presets template-id path)
 (cursor->
  :picker
  comp-popup
  states
  {:trigger (comp-i :edit 14 (hsl 200 80 50)), :style {:display :inline-block}}
  (fn [toggle!]
    (div
     {}
     (<> "Selected")
     (if (empty? presets)
       (div {} (<> "Nothing selected" {:color (hsl 0 0 80)}))
       (list->
        {:style ui/row}
        (->> presets
             (map
              (fn [preset]
                [preset
                 (comp-preset
                  preset
                  (fn [e d! m!]
                    (d!
                     :template/node-preset
                     {:template-id template-id, :path path, :op :remove, :value preset})))])))))
     (<> "Available")
     (list->
      {:style ui/row}
      (->> (difference builtin-presets presets)
           (map
            (fn [preset]
              [preset
               (comp-preset
                preset
                (fn [e d! m!]
                  (d!
                   :template/node-preset
                   {:template-id template-id, :path path, :op :add, :value preset})))]))))))))

(defcomp
 comp-presets
 (states presets template-id path)
 (div
  {}
  (<> "Presets")
  (=< 8 nil)
  (cursor-> :edit comp-presets-picker states presets template-id path)
  (list->
   {:style (merge ui/row {:padding "0 8px"})}
   (->> presets
        (map
         (fn [preset]
           [preset
            (comp-preset
             preset
             (fn [e d! m!]
               (d!
                :template/node-preset
                {:template-id template-id, :path path, :op :remove, :value preset})))]))))))