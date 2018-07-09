<b:base title="List Complete">
    <div class="container">

      <!-- Static navbar -->
      <nav class="navbar navbar-default">
        <div class="container-fluid">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">CS13304</a>
          </div>
          <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
              <li><a href="${pageContext.request.contextPath}">Home</a></li>
              <li><a href="${pageContext.request.contextPath}/upload">Upload</a></li>
              <li class="active"><a href="${pageContext.request.contextPath}/list">Download</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
              <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </nav>

      <!-- Main component for a primary marketing message or call to action -->
	<div>
		<ol class="breadcrumb">
         <li><a href="${pageContext.request.contextPath}/list">Download</a></li>
 		 <li class="active">${path }</li>
	  </ol>
		<c:if test="${fn:length(requestScope.paths) gt 0}">
			<table class="table table-striped">
					<tr>
						<th>Name</th>
						<th>Path</th>
						<th>Size (B)</th>
					</tr>
					<c:forEach items="${paths}" var="element">
						<tr>
							<td><a href="files?fileName=${element}">${element.getFileName()}</a></td>
							<td>${element}</td>
							<td>${element.toFile().length() }</td>
	
						</tr>
					</c:forEach>
				</table>
		</c:if>
		<c:if test="${not empty warnings }">
				<c:forEach var="warning" items="${warnings }">
					<div class="alert alert-info" role="alert">${warning }</div>
				</c:forEach>
		</c:if>
		<c:if test="${not empty errors }">
				<c:forEach var="error" items="${errors }">
					<div class="alert alert-danger" role="alert">${error }</div>
				</c:forEach>
		</c:if>
	</div>

    </div> <!-- /container -->
 </b:base>