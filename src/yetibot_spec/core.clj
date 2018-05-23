(ns yetibot-spec.core
(:gen-class))

(require '[clojure.pprint :as pprint])
(require '[clojure.spec.alpha :as spec])
(require '[expound.alpha :as expound])
(require '[clojure.string :as string])

(spec/def ::user-id   (spec/and pos-int? #(>= % 1000) #(< % Integer/MAX_VALUE)))
(spec/def ::unique-id (spec/or :name string? :id pos-int?))

(spec/def ::comment-ver1 (spec/or :filled string? :empty nil?))
(spec/def ::comment-ver2 (spec/nilable string?))


(spec/def ::username string?)
(spec/def ::password (spec/and string?
                         #(>= (count %) 8)))
(spec/def ::super-secure-signup (spec/keys :req-un [::username ::password]))

(spec/def ::non-empty-string (spec/and string? seq))


;; stolen from
;; https://stackoverflow.com/questions/43348511/what-is-generative-testing-in-clojure/43349674
(spec/def ::non-blank-string
  (spec/and string? #(not (string/blank? %))))

(spec/def ::non-blank-string2  #(and (string? %) (not (string/blank? %))))

;;(spec/def ::non-blank-string (and (string?) (not (string/blank?))))

(spec/def ::name string?)
(spec/def ::age pos-int?)
(spec/def ::user (spec/keys :req [::name] :req-un [::age]))

(defn explain-validation->data
    [validation-key value]
    (println "validation-key" validation-key)
    (println "value" value)
    (-> (spec/explain-data validation-key value) (pprint/pprint))
    (println))

(defn -main
    [& args]

    (let [ids [nil [] 1/2 0.5 1 10000000000 "*?"]]
         (doseq [id ids]
             (explain-validation->data ::user-id id)))

    (let [ids [nil [] 1/2 0.5 -1]]
         (doseq [id ids]
             (explain-validation->data ::unique-id id)))

    (explain-validation->data ::super-secure-signup  {:username "foo"
                         :password "123456789"}))

   (explain-validation->data ::non-empty-string "")
   (explain-validation->data ::non-blank-string "    mmmm")
;;   (explain-validation->data ::non-blank-string2 "    mmmm")

   (expound/expound ::user {})
   (expound/expound ::non-blank-string "            ")
