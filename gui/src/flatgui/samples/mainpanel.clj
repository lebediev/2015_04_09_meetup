; Copyright (c) 2015 Denys Lebediev and contributors. All rights reserved.
; The use and distribution terms for this software are covered by the
; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
; which can be found in the file LICENSE at the root of this distribution.
; By using this software in any fashion, you are agreeing to be bound by
; the terms of this license.
; You must not remove this notice, or any other, from this software.

(ns flatgui.samples.mainpanel
      (:require [flatgui.util.matrix :as m]
            [flatgui.base :as fg]
            [flatgui.awt :as awt]
            [flatgui.testsuite]
            [flatgui.widgets.panel :as panel]
            [flatgui.widgets.label :as label]
            [flatgui.widgets.textfield :as textfield]
            [flatgui.widgets.window :as window]
            [flatgui.widgets.button :as button]
            [flatgui.widgets.checkbox :as checkbox]
            [clojure.string]))


;(fg/defevolverfn processed-text-evolver :text
;  (let [text (:text (get-property [:editor] :model))]
;    (clojure.string/replace text " " "")))

(fg/defevolverfn processed-text-evolver :text
  (let [text (:text (get-property [:editor] :model))
        up? (get-property [:uppercase] :pressed)]
    (clojure.string/replace (if up? (clojure.string/upper-case text) text) " " "")))

(fg/defevolverfn result-box-evolver :model
  (let [reason (fg/get-reason)]
    (if (and (map? reason) (= :query-result (:event-type reason)))
      (let [text-from-server (:event-data reason)]
        {:text text-from-server :caret-pos (awt/strlen text-from-server) :selection-mark 0})



      (textfield/text-model-evolver component)

      )))

(def sample-dialog
  (fg/defcomponent
    window/window
    :dialog
    {:clip-size (m/defpoint 5 8)
     :position-matrix (m/translation 1 1)
     :text "Sample Dialogue"}

    (fg/defcomponent
      label/label
      :enter-label
      {:text "Enter text:"
       :clip-size (m/defpoint 1.25 0.375)
       :position-matrix (m/translation 0.0625 0.75)})

    (fg/defcomponent
      textfield/textfield
      :editor
      {:clip-size (m/defpoint 2.0 0.375)
       :position-matrix (m/translation 1.25 0.75)})

    (fg/defcomponent
      checkbox/checkbox
      :uppercase
      {:text "Upcase"
       :clip-size (m/defpoint 1.5 0.25)
       :position-matrix (m/translation 3.375 0.875)})

    (fg/defcomponent
      label/label
      :processed-label
      {:text ""
       :clip-size (m/defpoint 4.5 0.375)
       :position-matrix (m/translation 0.0625 1.25)

       :evolvers {:text processed-text-evolver}
       })

    (fg/defcomponent
      button/button
      :query-server
      {:text "Query"
       :clip-size (m/defpoint 2.0 0.375)
       :position-matrix (m/translation 0.125 1.75)})

    (fg/defcomponent
      textfield/textfield
      :result-box
      {:clip-size (m/defpoint 2.0 0.375)
       :position-matrix (m/translation 1.25 2.25)
       :evolvers {:model result-box-evolver}})

    ))


(def raw-mainpanel
  (flatgui.app/defroot
    (fg/defcomponent panel/panel :main
      {:clip-size (m/defpoint 32 20)
       :background (awt/color 0 0 0)
       :theme flatgui.theme/dark}

      sample-dialog)))

(def main-panel raw-mainpanel)

;(def main-panel
;  (flatgui.testsuite/simulate-string-type raw-mainpanel "abc def" [:main :dialog :editor]))

;(def main-panel
;  (->
;    (flatgui.testsuite/simulate-mouse-click raw-mainpanel [:main :dialog :uppercase])
;    (flatgui.testsuite/simulate-string-type "abc def" [:main :dialog :editor])))
;
;
;(println (str "Test result: " (= "abcdef" (get-in main-panel [:children :dialog :children :processed-label :text]))))