; Copyright (c) 2015 Denys Lebediev and contributors. All rights reserved.
; The use and distribution terms for this software are covered by the
; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
; which can be found in the file LICENSE at the root of this distribution.
; By using this software in any fashion, you are agreeing to be bound by
; the terms of this license.
; You must not remove this notice, or any other, from this software.

(ns flatgui.samples.meetup
  (:require [flatgui.app]
            [flatgui.skins.flat]
            [flatgui.samples.mainpanel]))

(def main (flatgui.app/defroot flatgui.samples.mainpanel/main-panel))