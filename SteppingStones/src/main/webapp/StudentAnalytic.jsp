<%@page import="java.util.ArrayList"%>
<%@page import="model.LevelDAO"%>
<%@page import="java.util.List"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
<style>
    #filter_container{
        position: block;
        background:#666;
        padding:20px;
    }
</style>


<div class="col-md-10" id="filter_container">
    <form class="form-inline">
        <div class="col-md-2"></div>
        <div class="form-group col-md-4">
            <label style="color:white">Level : </label>

                <select name="levelID" class="form-control levelSelect" id="levelID">
                    <option value="">Select Level</option>
                    <%  
                        LevelDAO levels = new LevelDAO();
                        ArrayList<String> levelList = levels.retrieveAllLevels();

                        for (String level : levelList) {
                            out.println("<option value='" + LevelDAO.retrieveLevelID(level) + "'>" + level + "</option>");
                        }
                    %>
                </select>
             
        </div>
        <div class="form-group col-md-6">
            <label style="color:white">Subject :</label>
            <select class="form-control" id="subject">
                <option value="0">Select Level first</option>
            </select>
        </div>
       
    </form>
</div>
<br/><br/>

<div class="col-md-10">
    <div id="schoolChart" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</div>


<div class="col-md-10">
    <div id="tuitionChart" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</div>




    
</div>
</div>

<%@include file="footer.jsp"%>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="https://code.highcharts.com/modules/data.js"></script>
<script src="https://code.highcharts.com/modules/drilldown.js"></script>

<script>
    $(".levelSelect").change(function () {
        action = 'retrieveSubjectOptions';
        levelID = $(".levelSelect").val();
        
        $.ajax({
            type: 'POST',
            url: 'AdminScheduleServlet',
            dataType: 'JSON',
            data: {levelID: levelID, action: action},
            success: function (data) {
                $("#subject").empty();
                $("#container").empty();
                $("#assign_tutor").empty();
                $("#subject").append('<option value="0">Select Subject</option>');
                for (var i = 0; i < data.subject.length; i++) {
                    $("#subject").append('<option value="' + data.subject[i].id + '">' + data.subject[i].name + '</option>');
                }
            }
        });
    });
    
    $("#subject").change(function () {
        var subSelect = $("#subject").val();
       
        if(subSelect.length !== 0){
            var lvlName = $(".levelSelect option:selected").html();
            var subName = $("#subject option:selected").html();
            $(function () {
                // Create the chart
                
                $('#schoolChart').highcharts({
                    "title": {
                        text: 'Center Grade for '+subName+"-"+lvlName 
                    },
                    "subtitle": {
                        text: 'Can be further breakdown by clicking on each bar'
                    },
                    "chart": {
                      "type": "column"
                    },
                    "credits": false,
                    "legend": {
                      "enabled": true,
                      "layout": "vertical",
                      "align": "right",
                      "verticalAlign": "middle",
                      "borderWidth": 0
                    },
                    "xAxis": {
                      "labels": {
                        "rotation": -90
                      },
                      "type": "category"
                    },
                    "yAxis": {
                      "min": 0,
                      "title": {
                        "text": ""
                      }
                    },
//                    "plotOptions": {
//                      "series": {
//                          
//                        "animation": false,
//                        "showInLegend": true,
//                        "dataLabels": {
//                          "enabled": false,
//                          "color": "grey",
//                          "style": {
//                            "text-shadow": "0 0 2px black"
//                          }
//                        },
//                        "stacking": null
//                      }
//                    },
                  
                    "series": [
                      {
                        "name": "Grade A",
                        "cropThreshold": 500,
                        "type": "column",
                        "data": [
                          {
                            "name": "CA1",
                            "y": 0,
                            "drilldown": "0|CA1"
                          },
                          {
                            "name": "SA1",
                            "y": 0,
                            "drilldown": "0|SA1"
                          },
                          {
                            "name": "CA2",
                            "y": 0,
                            "drilldown": "0|CA2"
                          },
                          {
                            "name": "SA2",
                            "y": 1,
                            "drilldown": "0|SA2"
                          }
                        ]
                      },
                      {
                        "name": "Grade B",
                        "cropThreshold": 500,
                        "type": "column",
                        "data": [
                          {
                            "name": "CA1",
                            "y": 1,
                            "drilldown": "1|CA1"
                          },
                          {
                            "name": "SA1",
                            "y": 3,
                            "drilldown": "1|SA1"
                          },
                          {
                            "name": "CA2",
                            "y": 1,
                            "drilldown": "1|CA2"
                          },
                          {
                            "name": "SA2",
                            "y": 1,
                            "drilldown": "1|SA2"
                          }
                        ]
                      },
                      {
                        "name": "Grade C",
                        "cropThreshold": 500,
                        "type": "column",
                        "data": [
                          {
                            "name": "CA1",
                            "y": 1,
                            "drilldown": "2|CA1"
                          },
                          {
                            "name": "SA1",
                            "y": 2,
                            "drilldown": "2|SA1"
                          },
                          {
                            "name": "CA2",
                            "y": 2,
                            "drilldown": "2|CA2"
                          },
                          {
                            "name": "SA2",
                            "y": 2,
                            "drilldown": "2|SA2"
                          }
                        ]
                      },
                      {
                        "name": "Grade D",
                        "cropThreshold": 500,
                        "type": "column",
                        "data": [
                          {
                            "name": "CA1",
                            "y": 3,
                            "drilldown": "3|CA1"
                          },
                          {
                            "name": "SA1",
                            "y": 3,
                            "drilldown": "3|SA1"
                          },
                          {
                            "name": "CA2",
                            "y": 2,
                            "drilldown": "3|CA2"
                          },
                          {
                            "name": "SA2",
                            "y": 4,
                            "drilldown": "3|SA2"
                          }
                        ]
                      },
                      {
                        "name": "Grade E",
                        "cropThreshold": 500,
                        "type": "column",
                        "data": [
                          {
                            "name": "CA1",
                            "y": 0,
                            "drilldown": "4|CA1"
                          },
                          {
                            "name": "SA1",
                            "y": 0,
                            "drilldown": "4|SA1"
                          },
                          {
                            "name": "CA2",
                            "y": 1,
                            "drilldown": "4|CA2"
                          },
                          {
                            "name": "SA2",
                            "y": 1,
                            "drilldown": "4|SA2"
                          }
                        ]
                      },
                      {
                        "name": "Grade F",
                        "cropThreshold": 500,
                        "type": "column",
                        "data": [
                          {
                            "name": "CA1",
                            "y": 1,
                            "drilldown": "5|CA1"
                          },
                          {
                            "name": "SA1",
                            "y": 1,
                            "drilldown": "5|SA1"
                          },
                          {
                            "name": "CA2",
                            "y": 0,
                            "drilldown": "5|CA2"
                          },
                          {
                            "name": "SA2",
                            "y": 0,
                            "drilldown": "5|SA2"
                          }
                        ]
                      }
//                      ,
//                      {
//                        type: 'spline',
//                        name: 'Average',
//                        data: [3, 2.67, 3, 6.33],
//                        marker: {
//                            lineWidth: 2,
//                            lineColor: Highcharts.getOptions().colors[3],
//                            fillColor: 'white'
//                        }
//                       }
                    ],
                    "drilldown": {
                           "plotOptions": {
        series: {
            cursor: 'pointer',
            point: {
                events: {
                    click: function () {
                        location.href = 'https://en.wikipedia.org/wiki/' +
                            this.options.key;
                    }
                }
            }
        }
    },
                      "series": [
                        // Grade A
                        {
                          "id": "0|CA1",
                          "name": [
                            "Grade A students for CA1"
                          ],
                          "type": "column",
                          "data": [
                          
                          ]
                        },
                        {
                          "id": "0|SA1",
                          "name": [
                            "Grade A students for SA1"
                          ],
                          "type": "column",
                          "data": [
                          ]
                        },
                        {
                          "id": "0|CA2",
                          "name": [
                            "Grade A students for CA2"
                          ],
                          "type": "column",
                          "data": [
                          ]
                        },
                        {
                          "id": "0|SA2",
                          "name": [
                            "Grade A students for SA2"
                          ],
                          "type": "column",
                          "data": [
                              [ "Wan Ik",88]
                          ]
                        },

                        // Grade B
                        {
                          "id": "1|CA1",
                          "name": [
                            "Grade B Students For CA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "<a href='IndividualGradeTracking.jsp?studentID=24'>Elton Lee</a>",
                              63
                            ]
                          ]
                        },
                        {
                          "id": "1|SA1",
                          "name": [
                            "Grade B Students For SA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Cybele Hu",
                              69.5
                            ],
                            [
                              "Elton Lee",
                              60
                            ],
                            [
                              "Ellie Oon",
                              60
                            ]
                          ]
                        },
                        {
                          "id": "1|CA2",
                          "name": [
                            "Grade B Students For CA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Ellie Oon",
                              64
                            ]
                          ]
                        },
                        {
                          "id": "1|SA2",
                          "name": [
                            "Grade B Students For SA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Elton Lee",
                              68
                            ]
                          ]
                        },

                        // Grade C (across exam)
                        {
                          "id": "2|CA1",
                          "name": [
                            "Grade C Students For CA1"
                          ],
                          "type": "column",
                          "data": [
                            [   
                              "Cybele Hu",
                              56
                            ]
                          ]
                        },
                        {
                          "id": "2|SA1",
                          "name": [
                            "Grade C Students For SA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Pet Min Xi",
                              57
                            ],
                            [
                              "Jia Xin",
                              54.5
                            ]
                          ]
                        },
                        {
                          "id": "2|CA2",
                          "name": [
                            "Grade C Students For CA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Elton Lee",
                              50
                            ],
                            [
                              "Jia Xin",
                              54.5
                            ]
                          ]
                        },
                        {
                          "id": "2|SA2",
                          "name": [
                            "Grade C Students For SA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Jia Xin",
                              58.5
                            ],
                            [
                              "Pek Min Xi",
                              54
                            ]
                          ]
                        },

                        // Grade D (across exam)
                        {
                          "id": "3|CA1",
                          "name": [
                            "Grade D Students For CA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Ellie Oon",
                              44
                            ],
                            [
                              "Pek Min Xi",
                              40
                            ],
                            [
                              "Sajitha Banu",
                              37
                            ]
                          ]
                        },
                        {
                          "id": "3|SA1",
                          "name": [
                            "Grade D Students For SA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Joshua Ng",
                              39
                            ],
                            [
                              "Sajitha Banu",
                              38
                            ],
                            [
                              "Rui Zhe",
                              32.5
                            ]
                          ]
                        },
                        {
                          "id": "3|CA2",
                          "name": [
                            "Grade D Students For CA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Joshua Ng",
                              48
                            ],
                            [
                              "Sajitha Banu",
                              46
                            ]
                          ]
                        },
                        {
                          "id": "3|SA2",
                          "name": [
                            "Grade D Students For SA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Ellie Oon",
                              48
                            ],
                            [
                              "Sajitha Banu",
                              47
                            ],
                            [
                              "Joshua Ng",
                              47
                            ],
                            [
                              "Rui Zhe",
                              46
                            ]
                          ]
                        },

                        //Grade E (across exam)
                        {
                          "id": "4|CA1",
                          "name": [
                            "Grade E Students For CA1"
                          ],
                          "type": "column",
                          "data": [
                           
                          ]
                        },
                        {
                          "id": "4|SA1",
                          "name": [
                            "Grade E Students For SA1"
                          ],
                          "type": "column",
                          "data": [
                          ]
                        },
                        {
                          "id": "4|CA2",
                          "name": [
                            "Grade E Students For CA2"
                          ],
                          "type": "column",
                          "data": [
                          ]
                        },
                        {
                          "id": "4|SA2",
                          "name": [
                            "Grade E Students For SA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Brayden",
                              21
                            ]
                          ]
                        },

                        // Grade F (across exam)
                        {
                          "id": "5|CA1",
                          "name": [
                            "Grade F Students For CA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Lim Qiu Yun",
                              16
                            ]
                          ]
                        },
                        {
                          "id": "5|SA1",
                          "name": [
                            "Grade F Students For SA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Aliah Natrisha",
                              13
                            ]
                          ]
                        },
                        {
                          "id": "5|CA2",
                          "name": [
                            "Grade F Students For CA2"
                          ],
                          "type": "column",
                          "data": [
                           
                          ]
                        },
                        {
                          "id": "5|SA2",
                          "name": [
                            "Grade F Students For SA2"
                          ],
                          "type": "column",
                          "data": [
                            
                          ]
                        }

                      ]
                    }
                });
                
                
//                $('#tuitionChart').highcharts({
//                    "title": null,
//                    "chart": {
//                      "type": "column"
//                    },
//                    "credits": false,
//                    "legend": {
//                      "enabled": true,
//                      "layout": "vertical",
//                      "align": "right",
//                      "verticalAlign": "middle",
//                      "borderWidth": 0
//                    },
//                    "xAxis": {
//                      "labels": {
//                        "rotation": -90
//                      },
//                      "type": "category"
//                    },
//                    "yAxis": {
//                      "min": 0,
//                      "title": {
//                        "text": ""
//                      }
//                    },
//                    "plotOptions": {
//                          "series": {
//                            "animation": false,
//                            "showInLegend": true,
//                            "dataLabels": {
//                              "enabled": false,
//                              "color": "grey",
//                              "style": {
//                                "text-shadow": "0 0 2px black"
//                              }
//                            },
//                            "stacking": null
//                          }
//                        },
//                    "series": [
//                        {
//                          "name": "Grade A",
//                          "cropThreshold": 500,
//                          "type": "column",
//                          "data": [
//                            {
//                              "name": "CA1",
//                              "y": 0,
//                              "drilldown": "0|CA1"
//                            },
//                            {
//                              "name": "SA1",
//                              "y": 0,
//                              "drilldown": "0|SA1"
//                            },
//                            {
//                              "name": "CA2",
//                              "y": 0,
//                              "drilldown": "0|CA2"
//                            },
//                            {
//                              "name": "SA2",
//                              "y": 0,
//                              "drilldown": "0|SA2"
//                            }
//                          ]
//                        },
//                        {
//                          "name": "Grade B",
//                          "cropThreshold": 500,
//                          "type": "column",
//                          "data": [
//                            {
//                              "name": "CA1",
//                              "y": 0,
//                              "drilldown": "1|CA1"
//                            },
//                            {
//                              "name": "SA1",
//                              "y": 1,
//                              "drilldown": "1|SA1"
//                            },
//                            {
//                              "name": "CA2",
//                              "y": 0,
//                              "drilldown": "1|CA2"
//                            },
//                            {
//                              "name": "SA2",
//                              "y": 0,
//                              "drilldown": "1|SA2"
//                            }
//                          ]
//                        },
//                        {
//                          "name": "Grade C",
//                          "cropThreshold": 500,
//                          "type": "column",
//                          "data": [
//                            {
//                              "name": "CA1",
//                              "y": 0,
//                              "drilldown": "2|CA1"
//                            },
//                            {
//                              "name": "SA1",
//                              "y": 0,
//                              "drilldown": "2|SA1"
//                            },
//                            {
//                              "name": "CA2",
//                              "y": 0,
//                              "drilldown": "2|CA2"
//                            },
//                            {
//                              "name": "SA2",
//                              "y": 0,
//                              "drilldown": "2|SA2"
//                            }
//                          ]
//                        },
//                        {
//                          "name": "Grade D",
//                          "cropThreshold": 500,
//                          "type": "column",
//                          "data": [
//                            {
//                              "name": "CA1",
//                              "y": 0,
//                              "drilldown": "3|CA1"
//                            },
//                            {
//                              "name": "SA1",
//                              "y": 3,
//                              "drilldown": "3|SA1"
//                            },
//                            {
//                              "name": "CA2",
//                              "y": 0,
//                              "drilldown": "3|CA2"
//                            },
//                            {
//                              "name": "SA2",
//                              "y": 2,
//                              "drilldown": "3|SA2"
//                            }
//                          ]
//                        },
//                        {
//                          "name": "Grade E",
//                          "cropThreshold": 500,
//                          "type": "column",
//                          "data": [
//                            {
//                              "name": "CA1",
//                              "y": 0,
//                              "drilldown": "4|CA1"
//                            },
//                            {
//                              "name": "SA1",
//                              "y": 1,
//                              "drilldown": "4|SA1"
//                            },
//                            {
//                              "name": "CA2",
//                              "y": 0,
//                              "drilldown": "4|CA2"
//                            },
//                            {
//                              "name": "SA2",
//                              "y": 3,
//                              "drilldown": "4|SA2"
//                            }
//                          ]
//                        },
//                        {
//                          "name": "Grade F",
//                          "cropThreshold": 500,
//                          "type": "column",
//                          "data": [
//                            {
//                              "name": "CA1",
//                              "y": 0,
//                              "drilldown": "5|CA1"
//                            },
//                            {
//                              "name": "SA1",
//                              "y": 3,
//                              "drilldown": "5|SA1"
//                            },
//                            {
//                              "name": "CA2",
//                              "y": 6,
//                              "drilldown": "2|CA2"
//                            },
//                            {
//                              "name": "SA2",
//                              "y": 4,
//                              "drilldown": "5|SA2"
//                            }
//                          ]
//                        }
//                    ],
//                    "drilldown": {
//                          "series": [
//                            {
//                              "id": "0|CA1",
//                              "name": [
//                                "Grade A CA1"
//                              ],
//                              "type": "column",
//                              "data": [
//
//                              ]
//                            },
//                            {
//                              "id": "0|SA1",
//                              "name": [
//                                "Grade A SA1"
//                              ],
//                              "type": "column",
//                              "data": [
//
//                              ]
//                            },
//                            {
//                              "id": "0|CA2",
//                              "name": [
//                                "Grade A CA2"
//                              ],
//                              "type": "column",
//                              "data": [
//
//                              ]
//                            },
//                            {
//                              "id": "0|SA2",
//                              "name": [
//                                "Grade A SA2"
//                              ],
//                              "type": "column",
//                              "data": [
//                              ]
//                            },
//
//                            // B grade
//                            {
//                              "id": "1|CA1",
//                              "name": [
//                                "Grade B CA1"
//                              ],
//                              "type": "column",
//                              "data": [
//                              ]
//                            },
//                            {
//                              "id": "1|SA1",
//                              "name": [
//                                "Grade B SA1"
//                              ],
//                              "type": "column",
//                              "data": [
//                                [
//                                  "Cybele Hu",
//                                  63
//                                ]
//                              ]
//                            },
//                            {
//                              "id": "1|CA2",
//                              "name": [
//                                "Grade B CA2"
//                              ],
//                              "type": "column",
//                              "data": [
//                              ]
//                            },
//                            {
//                              "id": "1|SA2",
//                              "name": [
//                                "Grade B SA2"
//                              ],
//                              "type": "column",
//                              "data": [
//                              ]
//                            },
//
//                            //C grade
//                            {
//                              "id": "2|CA1",
//                              "name": [
//                                "Grade C CA1"
//                              ],
//                              "type": "column",
//                              "data": [
//                              ]
//                            },
//                            {
//                              "id": "2|SA1",
//                              "name": [
//                                "Grade C SA1"
//                              ],
//                              "type": "column",
//                              "data": [
//                              ]
//                            },
//                            {
//                              "id": "2|CA2",
//                              "name": [
//                                "Grade C CA2"
//                              ],
//                              "type": "column",
//                              "data": [
//                              ]
//                            },
//                            {
//                              "id": "2|SA2",
//                              "name": [
//                                "Grade C SA2"
//                              ],
//                              "type": "column",
//                              "data": [
//                              ]
//                            },
//
//                            //D grade
//                            {
//                              "id": "3|CA1",
//                              "name": [
//                                "Grade D CA1"
//                              ],
//                              "type": "column",
//                              "data": [
//                              ]
//                            },
//                            {
//                              "id": "3|SA1",
//                              "name": [
//                                "Grade D SA1"
//                              ],
//                              "type": "column",
//                              "data": [
//                                [
//                                  "Pek Min Xi",
//                                  40
//                                ],
//                                [
//                                  "Elton Lee",
//                                  35
//                                ],
//                                [
//                                    "Ellie Oon",
//                                    35
//                                ]
//                              ]
//                            },
//                            {
//                              "id": "3|CA2",
//                              "name": [
//                                "Grade D CA2"
//                              ],
//                              "type": "column",
//                              "data": [
//                              ]
//                            },
//                            {
//                              "id": "3|SA2",
//                              "name": [
//                                "Grade D SA2"
//                              ],
//                              "type": "column",
//                              "data": [
//                                [
//                                  "Elton Lee",
//                                  41
//                                ],
//                                [
//                                  "Wan IK",
//                                  35
//                                ]
//                              ]
//                            },
//                            
//                            //E grade
//                            {
//                              "id": "4|CA1",
//                              "name": [
//                                "Grade E CA1"
//                              ],
//                              "type": "column",
//                              "data": [
//                              ]
//                            },
//                            {
//                              "id": "4|SA1",
//                              "name": [
//                                "Grade E SA1"
//                              ],
//                              "type": "column",
//                              "data": [
//                                [
//                                  "Sajitha Banu",
//                                  30
//                                ]
//                              ]
//                            },
//                            {
//                              "id": "4|CA2",
//                              "name": [
//                                "Grade E CA2"
//                              ],
//                              "type": "column",
//                              "data": [
//                              ]
//                            },
//                            {
//                              "id": "4|SA2",
//                              "name": [
//                                "Grade E SA2"
//                              ],
//                              "type": "column",
//                              "data": [
//                                [
//                                  "Wan IK",
//                                  35
//                                ],
//                                [
//                                  "Brayden Ng",
//                                  24
//                                ],
//                                [
//                                  "Ellie Oon",
//                                  22
//                                ]
//                              ]
//                            },
//
//                            // F grade
//                            {
//                              "id": "5|CA1",
//                              "name": [
//                                "Grade F CA1"
//                              ],
//                              "type": "column",
//                              "data": [
//                              ]
//                            },
//                            {
//                              "id": "5|SA1",
//                              "name": [
//                                "Grade F SA1"
//                              ],
//                              "type": "column",
//                              "data": [
//                                [
//                                  "Liow Rui Zhe",
//                                  15
//                                ],
//                                [
//                                  "Lim Qiu Yun",
//                                  13
//                                ],
//                                [
//                                  "Joshua Ng",
//                                  12
//                                ]
//                              ]
//                            },
//                            {
//                              "id": "5|CA2",
//                              "name": [
//                                "Grade F CA2"
//                              ],
//                              "type": "column",
//                              "data": [
//                                [
//                                  "Elton Lee",
//                                  16
//                                ],
//                                [
//                                  "Brayden Ng",
//                                  4
//                                ],
//                                [
//                                  "Jia Xin",
//                                  2
//                                ],
//                                [
//                                  "Sajitha Banu",
//                                  2
//                                ],
//                                [
//                                  "Joshua Ng",
//                                  2
//                                ]
//                              ]
//                            },
//                            {
//                              "id": "5|SA2",
//                              "name": [
//                                "Grade F SA2"
//                              ],
//                              "type": "column",
//                              "data": [
//                                [
//                                  "Jia Xin",
//                                  18
//                                ],
//                                [
//                                  "Sajitha Banu",
//                                  15
//                                ],
//                                [
//                                  "Joshua Ng",
//                                  10
//                                ],
//                                [
//                                  "Liow Rui Zhe",
//                                  5
//                                ]
//                              ]
//                            }
//
//                          ]
//                        }
//                });
            
                 
                
                
            });
            
   
        }
    });
    

    
   
</script>