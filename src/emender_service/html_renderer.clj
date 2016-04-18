;
;  (C) Copyright 2016  Pavel Tisnovsky
;
;  All rights reserved. This program and the accompanying materials
;  are made available under the terms of the Eclipse Public License v1.0
;  which accompanies this distribution, and is available at
;  http://www.eclipse.org/legal/epl-v10.html
;
;  Contributors:
;      Pavel Tisnovsky
;

(ns emender-service.html-renderer)

(require '[hiccup.core  :as hiccup])
(require '[hiccup.page  :as page])
(require '[hiccup.form  :as form])

(defn render-html-header
    "Renders part of HTML page - the header."
    []
    [:head
        [:title "Emender service"]
        [:meta {:name "Author"    :content "Pavel Tisnovsky"}]
        [:meta {:name "Generator" :content "Clojure"}]
        [:meta {:http-equiv "Content-type" :content "text/html; charset=utf-8"}]
        (page/include-css "bootstrap.min.css")
        (page/include-css "emender-service.css")
        ;(page/include-js  "bootstrap.min.js")
    ] ; head
)

; TODO - use configuration to fetch e-mail and link to Bugzilla or Jira
(defn render-html-footer
    "Renders part of HTML page - the footer."
    []
    [:div (str "<br /><br /><br /><br />Author: Pavel Tisnovsky")])

(defn render-navigation-bar-section
    "Renders whole navigation bar."
    []
    [:nav {:class "navbar navbar-inverse navbar-fixed-top" :role "navigation"}
        [:div {:class "container-fluid"}
            [:div {:class "row"}
                [:div {:class "col-md-4"}
                    [:div {:class "navbar-header"}
                        [:a {:href "/" :class "navbar-brand"} "Emender service"]
                    ] ; ./navbar-header
                ] ; col ends
                [:div {:class "col-md-4"}
                    [:div {:class "navbar-header"}
                        [:div {:class "navbar-brand"}
                            "&nbsp;" ; to be updated if needed
                        ]
                    ]
                ] ; col ends
                [:div {:class "col-md-4"}
                            "&nbsp;" ; to be updated if needed
                ] ; col ends
            ] ; row ends
        ] ; /.container-fluid
]); </nav>

(defn render-front-page
    "Render front page"
    []
    (page/xhtml
        (render-html-header)
        [:body {:style "padding-top:70px"}
            [:div {:class "container"}
                (render-navigation-bar-section)
                [:div {:class "col-md-10"}
                    [:h2 "Emender service"]
                    [:table
                        [:tr [:td [:a {:href "/job-list"} "Job list"]]]
                        [:tr [:td [:a {:href "/book-list"} "Book list"]]]
                        [:tr [:td "&nbsp;"]]
                        [:tr [:td [:a {:href "/configuration"} "Configuration"]]]
                        [:tr [:td "&nbsp;"]]
                        [:tr [:td [:a {:href "/operation-log"} "Operation log"]]]
                        [:tr [:td [:a {:href "/request-log"} "Request log"]]]
                        [:tr [:td [:a {:href "/errors"} "Error log"]]]
                        [:tr [:td [:a {:href "/results"} "Results log"]]]
                        [:tr [:td [:a {:href "/log"} "Common log"]]]
                    ]
                [:br][:br][:br][:br]
                (render-html-footer)
                ]
            ] ; </div class="container">
        ] ; </body>
    ))

(defn render-job-list-page
    "Render page with list of all jobs"
    [job-list]
    (page/xhtml
        (render-html-header)
        [:body {:style "padding-top:70px"}
            [:div {:class "container"}
                (render-navigation-bar-section)
                [:div {:class "col-md-10"}
                    [:h2 "Job list"]
                    [:table {:class "table table-striped table-condensed table-hover table-bordered"}
                        (for [job job-list]
                            [:tr [:td (:id job)]
                                 [:td [:a {:href (str "/job-info?id=" (:id job))} (:job job)]]
                                 [:td [:a {:href (str "/rendered-test-results?job=" (:id job))} "Test results page"]]])
                    ]
                [:div [:a {:href "/"} "Back"]]
                [:br][:br][:br][:br]
                (render-html-footer)
                ]
            ] ; </div class="container">
        ] ; </body>
    ))

(defn render-book-list-page
    "Render page with list of all books"
    [book-list]
    (page/xhtml
        (render-html-header)
        [:body {:style "padding-top:70px"}
            [:div {:class "container"}
                (render-navigation-bar-section)
                [:div {:class "col-md-10"}
                    [:h2 "Book list"]
                    [:table {:class "table table-striped table-condensed table-hover table-bordered"}
                        (for [book book-list]
                            [:tr [:td [:a {:href (str "/jobs-for-book?book=" (:job book))} (:job book)]]])
                    ]
                [:div [:a {:href "/"} "Back"]]
                [:br][:br][:br][:br]
                (render-html-footer)
                ]
            ] ; </div class="container">
        ] ; </body>
    ))

(defn render-job-info
    "Render page with information for the selected job"
    [job-info test-results]
    (page/xhtml
        (render-html-header)
        [:body {:style "padding-top:70px"}
            [:div {:class "container"}
                (render-navigation-bar-section)
                [:div {:class "col-md-10"}
                    [:h2 "Job info"]
                    [:table {:class "table table-striped table-condensed table-hover table-bordered"}
                        [:tr [:td "Job id"]
                             [:td (:id job-info)]]
                        [:tr [:td "Finished at"]
                             [:td (:datetime job-info)]]
                        [:tr [:td "Book name"]
                             [:td (:job job-info)]]
                        [:tr [:td "Git URL"]
                             [:td (or (:url job-info) "?")]]
                        [:tr [:td "Branch"]
                             [:td (or (:branch job-info) "?")]]
                        [:tr [:td "Tests"]
                             [:td (count test-results)]]
                        [:tr [:td "Test results page"]
                             [:td [:a {:href (str "/rendered-test-results?job=" (:id job-info))} (str "/rendered-test-results?job=" (:id job-info))]]]
                    ]
                    [:h3 "Results"]
                    [:ul
                        (for [test-result test-results]
                            [:li (key test-result)
                                [:ol
                                    (for [one-test (val test-result)]
                                        [:li (key one-test)
                                            [:ul
                                                (for [step (val one-test)]
                                                    [:li (get step "status")
                                                         "&nbsp;"
                                                         (get step "message")]
                                                )
                                        ]]
                                    )]
                            ])
                    ]
                [:div [:a {:href "/"} "Back"]]
                [:br][:br][:br][:br]
                (render-html-footer)
                ]
            ] ; </div class="container">
        ] ; </body>
    ))


(defn render-log
    [title table-header log-part]
    (page/xhtml
        (render-html-header)
        [:body {:style "padding-top:70px"}
            [:div {:class "container"}
                (render-navigation-bar-section)
                [:div {:class "col-md-10"}
                    [:h2 title]
                    [:table {:class "table table-striped table-condensed table-hover table-bordered"}
                        table-header
                        log-part
                    ]
                [:div [:a {:href "/"} "Back"]]
                [:br][:br][:br][:br]
                (render-html-footer)
                ]
            ] ; </div class="container">
        ] ; </body>
    ))

(defn render-operation-log-page
    [log]
    (render-log "Operation log"
        [:tr [:th "ID"] [:th "Timestamp"] [:th "Job"] [:th "GIT URL"] [:th "Branch"] [:th "Operation"]]
        (for [l log]
            [:tr [:td (:id l)]
                 [:td (:datetime l)]
                 [:td (:job l)]
                 [:td (:url l)]
                 [:td (:branch l)]
                 [:td (:operation l)]
            ])))

(defn render-request-log-page
    [log]
    (render-log "Request log"
        [:tr [:th "ID"] [:th "Timestamp"] [:th "URI"] [:th "IP"] [:th "Params"] [:th "Useragent"]]
        (for [l log]
            [:tr [:td (:id l)]
                 [:td (:datetime l)]
                 [:td (:uri l)]
                 [:td (:ipaddress l)]
                 [:td (:params l)]
                 [:td (:useragent l)]
            ])))

(defn render-error-log-page
    [log]
    (render-log "Error log"
        [:tr [:th "ID"] [:th "Timestamp"] [:th "Job"] [:th "GIT URL"] [:th "Branch"] [:th "Message"] [:th "Stacktrace"]]
        (for [l log]
            [:tr [:td (:id l)]
                 [:td (:datetime l)]
                 [:td (:job l)]
                 [:td (:url l)]
                 [:td (:branch l)]
                 [:td (:message l)]
                 [:td (:stacktrace l)]
            ])))

(defn render-log-page
    [log]
    (render-log "Log"
        [:tr [:th "ID"] [:th "Timestamp"] [:th "Job"] [:th "GIT URL"] [:th "Branch"] [:th "Operation"] [:th "Message"]]
        (for [l log]
            [:tr [:td (:id l)]
                 [:td (:datetime l)]
                 [:td (:job l)]
                 [:td (:url l)]
                 [:td (:branch l)]
                 [:td (:operation l)]
                 [:td (:message l)]
            ])))

(defn render-results-page
    [results]
    (render-log "Results"
        [:tr [:th "ID"] [:th "Timestamp"] [:th "Job"] [:th "GIT URL"] [:th "Branch"] [:th "Results"]]
        (for [result results]
            [:tr [:td (:id result)]
                 [:td (:datetime result)]
                 [:td (:job result)]
                 [:td (:url result)]
                 [:td (:branch result)]
                 [:td [:a {:href (str "/job-info?id=" (:id result))} "Here"]]
            ])))

