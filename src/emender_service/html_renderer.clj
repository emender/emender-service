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
        (page/include-js  "bootstrap.min.js")
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
        [:body
            [:div {:class "container"}
                (render-navigation-bar-section)
                [:div {:class "col-md-10"}
                    [:h2 "Emender service"]
                    [:p "xyzzy"]
                ]
                [:br][:br][:br][:br]
                (render-html-footer)
            ] ; </div class="container">
        ] ; </body>
    ))

