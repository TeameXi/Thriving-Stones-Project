<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
<style>
    .autocomplete-items {
        position: absolute;
        border: 1px solid #d4d4d4;
        border-bottom: none;
        border-top: none;
        z-index: 99;
        /*position the autocomplete items to be the same width as the container:*/
        top: 100%;
        left: 0;
        right: 0;
    }
    .autocomplete-items div {
        padding: 10px;
        cursor: pointer;
        background-color: #fff; 
        border-bottom: 1px solid #d4d4d4; 
    }
    .autocomplete-items div:hover {
        /*when hovering an item:*/
        background-color: #e9e9e9; 
    }
    .autocomplete-active {
        /*when navigating through the items using the arrow keys:*/
        background-color: DodgerBlue !important; 
        color: #ffffff; 
    }
    
    .event-info{
        margin-left: 20px;
    }
</style>
<div class="col-md-10">
    <div style="text-align: center;margin: 20px;"><span class="tab_active">Display Schedule</span></h5></div>
    <div class="row" id="errorMsg"></div>
    <div class="row">
        <form autocomplete="off" id="searchClassesId" onsubmit="return searchClasses();">
            <input type="hidden" value="<%=branch_id%>" id="branch_id"/>
            <div class="form-group">
                <label class="col-lg-2 control-label">Term</label>  
                <div class="col-lg-7 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="zmdi zmdi-hourglass-outline"></i></span>

                        <select id="term" name="term" class="form-control">
                            <option value="1">Term I</option>
                            <option value="2">Term II</option>
                            <option value="3">Term III</option>
                            <option value="4">Term IV</option>
                        </select>
                    </div>
                </div>
            </div>
            <br/><br/><br/>


            <div class="form-group">
                <label class="col-lg-2 control-label">Level</label>  
                <div class="col-lg-7 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="zmdi zmdi-search"></i></span>  
                        <input id="level" type="text" name="level"  class="form-control">
                    </div>
                </div>
            </div>
            <br/><br/>
            
            
            <div class="form-group">
                <label class="col-lg-2 control-label">Year</label>  
                <div class="col-lg-7 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="zmdi zmdi-calendar"></i></span>  
                        <input id="planningYear" type="number" name="planningYear"  class="form-control">
                    </div>
                </div>
            </div>
            <br/><br/>

            <div class="form-group">
                <div class="col-lg-2 col-lg-offset-2">
                    <button type="submit" class="btn btn-default">Plan Classes</button>
                </div>
            </div>

        </form>
    </div>
    <br/>   
    
    
</div>
</div>
</div>
<%@include file="footer.jsp"%>
<script src="${pageContext.request.contextPath}/styling/js/jquery.autocomplete.js"></script>
<script>
    var level = ["Primary 1", "Primary 2", "Primary 3", "Primary 4", "Primary 5", "Primary 6", "Secondary 1", "Secondary 2", "Secondary 3", "Secondary 4"];
    autocomplete(document.getElementById("level"), level);
</script>
