
<b:base title="Upload File">
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
              <li class="active"><a href="${pageContext.request.contextPath}/upload">Upload</a></li>
              <li><a href="${pageContext.request.contextPath}/list">Download</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
              <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </nav>

      <!-- Main component for a primary marketing message or call to action -->
		<form method="POST" action="upload" enctype="multipart/form-data">
				<div class="form-group">
					 <label for="name">Name</label>
    				 <input type="text" class="form-control" id="name" 
    				 placeholder="File name" name="name" required="true">
				</div>
				<div class="form-group">
					 <label for="path">Path</label>
    				 <input type="text" class="form-control" id="path" 
    				 placeholder="File path" name="path" required="true">
				</div>
				<div class="form-group">
					<label for="exampleInputFile">File input</label>
				    <input type="file" id="file" name="file"  required="true">
				    <p class="help-block">This file will be uploaded</p>
				</div>
			<button type="submit" class="btn btn-default">Upload</button>
		    </form>

    </div> <!-- /container -->
 </b:base>