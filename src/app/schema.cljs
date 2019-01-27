
(ns app.schema )

(def markup
  {:id nil, :type nil, :props {}, :layout nil, :presets #{}, :styles {}, :children {}})

(def mock {:id nil, :data nil, :name nil})

(def router {:name nil, :title nil, :data {}, :router nil})

(def session
  {:user-id nil,
   :id nil,
   :nickname nil,
   :router (do router {:name :home, :data nil, :router nil}),
   :messages {}})

(def template {:id nil, :name nil, :mocks (do mock {}), :markup (do markup nil)})

(def user {:name nil, :id nil, :nickname nil, :avatar nil, :password nil})

(def database {:sessions (do session {}), :users (do user {}), :templates (do template {})})
