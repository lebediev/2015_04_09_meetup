; Copyright (c) 2015 Denys Lebediev and contributors. All rights reserved.
; The use and distribution terms for this software are covered by the
; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
; which can be found in the file LICENSE at the root of this distribution.
; By using this software in any fashion, you are agreeing to be bound by
; the terms of this license.
; You must not remove this notice, or any other, from this software.

(ns flatgui.samples.backendconnector
  (:import (flatgui.core IFGEvolveConsumer)))


(defn commit-button-connector [fg-container-accessor]
  (reify IFGEvolveConsumer
    (getTargetPaths [_this]
      (list [:main :dialog :query-server]))
    (acceptEvolveResult [_this _sid container]

      ;TODO
      ; use :pressed-trigger

      (let [pressed (get-in container [:children :dialog :children :query-server :pressed])]
        (if pressed
          (do
            (println "COMMIT BUTTON PRESSED")
            (println "Imagine we send some request to resver here...")
            (.feedTargetedEvent fg-container-accessor [:main :dialog :result-box]
                                {:event-type :query-result
                                 :event-data (str (System/currentTimeMillis))})))))))

(defn connect-container [fg-container-accessor]
  (do
    (.addEvolveConsumer fg-container-accessor (commit-button-connector fg-container-accessor))))