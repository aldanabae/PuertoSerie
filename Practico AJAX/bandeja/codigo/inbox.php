<!DOCTYPE html>
<html lang="en">

	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>SSI</title>

		<meta name="description" content="Mailbox with some customizations as described in docs" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="../assets/css/bootstrap.css" />
		<link rel="stylesheet" href="../assets/css/font-awesome.css" />

		<!-- page specific plugin styles -->

		<!-- text fonts -->
		<link rel="stylesheet" href="../assets/css/ace-fonts.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="../assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="../assets/css/ace-part2.css" class="ace-main-stylesheet" />
		<![endif]-->

		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="../assets/css/ace-ie.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->
		<script src="../assets/js/ace-extra.js"></script>
		<script src="ajax.js"></script>
		<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

		<!--[if lte IE 8]>
		<script src="../assets/js/html5shiv.js"></script>
		<script src="../assets/js/respond.js"></script>
		<![endif]-->
		
	</head>

	<body class="no-skin">
		<!-- #section:basics/navbar.layout -->
		<div id="navbar" class="navbar navbar-default">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

			<div class="navbar-container" id="navbar-container">
				<!-- #section:basics/sidebar.mobile.toggle -->
				<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
					<span class="sr-only">Toggle sidebar</span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>
				</button>

				<!-- /section:basics/sidebar.mobile.toggle -->
				<div class="navbar-header pull-left">
					<!-- #section:basics/navbar.layout.brand -->
					<a href="#" class="navbar-brand">
						<small>
							<i class="fa fa-leaf"></i>
							SSI
						</small>
					</a>

					<!-- /section:basics/navbar.layout.brand -->

					<!-- #section:basics/navbar.toggle -->

					<!-- /section:basics/navbar.toggle -->
				</div>

				<!-- #section:basics/navbar.dropdown -->
				

				<!-- /section:basics/navbar.dropdown -->
			</div><!-- /.navbar-container -->
		</div>

		<!-- /section:basics/navbar.layout -->
		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<!-- #section:basics/sidebar -->
			<div id="sidebar" class="sidebar                  responsive">
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
				</script>

				<div class="sidebar-shortcuts" id="sidebar-shortcuts">
					<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
						<button class="btn btn-success">
							<i class="ace-icon fa fa-signal"></i>
						</button>

						<button class="btn btn-info">
							<i class="ace-icon fa fa-pencil"></i>
						</button>

						<!-- #section:basics/sidebar.layout.shortcuts -->
						<button class="btn btn-warning">
							<i class="ace-icon fa fa-users"></i>
						</button>

						<button class="btn btn-danger">
							<i class="ace-icon fa fa-cogs"></i>
						</button>

						<!-- /section:basics/sidebar.layout.shortcuts -->
					</div>

					<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span>

						<span class="btn btn-info"></span>

						<span class="btn btn-warning"></span>

						<span class="btn btn-danger"></span>
					</div>
				</div><!-- /.sidebar-shortcuts -->

			

				<!-- #section:basics/sidebar.layout.minimize -->
				<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">

					<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
				</div>
				<img class="middle" alt="John's Avatar" src="../assets/images/nosotros1.jpg" width="100" />
						<img class="middle" alt="John's Avatar" src="../assets/images/nosotros2.jpg" width="200" height="100" />
				<!-- /section:basics/sidebar.layout.minimize -->
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
			</div>

			<!-- /section:basics/sidebar -->
			<div class="main-content">
				

					<!-- /section:basics/content.breadcrumbs -->
					<div class="page-content">
					
						<!-- #section:settings.box -->
						<div class="ace-settings-container" id="ace-settings-container">
							
							

						</div><!-- /.ace-settings-container -->

						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
								Bandeja de Entrada
							
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<div class="row">
									<div class="col-xs-12">
										<!-- #section:pages/inbox -->
										<div class="tabbable">
											<ul id="inbox-tabs" class="inbox-tabs nav nav-tabs padding-16 tab-size-bigger tab-space-1">
											

												<!-- /section:pages/inbox.compose-btn -->
												<li class="active">
													<a data-toggle="tab" href="#inbox" data-target="inbox">
														<i class="blue ace-icon fa fa-inbox bigger-130"></i>
														<span class="bigger-110">Inbox</span>
													</a>
												</li>
											</ul>

											<div class="tab-content no-border no-padding">
												<div id="inbox" class="tab-pane in active">
													<div class="message-container">
														<!-- #section:pages/inbox.navbar -->
														<div id="id-message-list-navbar" class="message-navbar clearfix">
															<div class="message-bar">
																

																<div class="message-toolbar hide">
																	<div class="inline position-relative align-left">
																		<button type="button" class="btn-white btn-primary btn btn-xs dropdown-toggle" data-toggle="dropdown">
																			<span class="bigger-110">Action</span>

																			<i class="ace-icon fa fa-caret-down icon-on-right"></i>
																		</button>

																		<ul class="dropdown-menu dropdown-lighter dropdown-caret dropdown-125">
																			<li> 
																				<a href="#">
																					<i class="ace-icon fa fa-mail-reply blue"></i>&nbsp; Reply
																				</a>
																			</li>

																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-mail-forward green"></i>&nbsp; Forward
																				</a>
																			</li>

																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-folder-open orange"></i>&nbsp; Archive
																				</a>
																			</li>

																			<li class="divider"></li>

																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-eye blue"></i>&nbsp; Mark as read
																				</a>
																			</li>

																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-eye-slash green"></i>&nbsp; Mark unread
																				</a>
																			</li>

																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-flag-o red"></i>&nbsp; Flag
																				</a>
																			</li>

																			<li class="divider"></li>

																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-trash-o red bigger-110"></i>&nbsp; Delete
																				</a>
																			</li>
																		</ul>
																	</div>

																	<div class="inline position-relative align-left">
																		<button type="button" class="btn-white btn-primary btn btn-xs dropdown-toggle" data-toggle="dropdown">
																			<i class="ace-icon fa fa-folder-o bigger-110 blue"></i>
																			<span class="bigger-110">Move to</span>

																			<i class="ace-icon fa fa-caret-down icon-on-right"></i>
																		</button>

																		<ul class="dropdown-menu dropdown-lighter dropdown-caret dropdown-125">
																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-stop pink2"></i>&nbsp; Tag#1
																				</a>
																			</li>

																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-stop blue"></i>&nbsp; Family
																				</a>
																			</li>

																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-stop green"></i>&nbsp; Friends
																				</a>
																			</li>

																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-stop grey"></i>&nbsp; Work
																				</a>
																			</li>
																		</ul>
																	</div>

																	<button type="button" class="btn btn-xs btn-white btn-primary">
																		<i class="ace-icon fa fa-trash-o bigger-125 orange"></i>
																		<span class="bigger-110">Delete</span>
																	</button>
																</div>
															</div>

															<div>
																

																<!-- #section:pages/inbox.navbar-search -->
																<div class="nav-search minimized">
																	<form class="form-search">
																		<span class="input-icon">
																			<input type="text" autocomplete="off" class="input-small nav-search-input" placeholder="Search inbox ..." />
																			<i class="ace-icon fa fa-search nav-search-icon"></i>
																		</span>
																	</form>
																</div>

																<!-- /section:pages/inbox.navbar-search -->
															</div>
														</div>

														<div id="id-message-item-navbar" class="hide message-navbar clearfix">
															<div class="message-bar">
																<div class="message-toolbar">
																	<div class="inline position-relative align-left">
																		<button type="button" class="btn-white btn-primary btn btn-xs dropdown-toggle" data-toggle="dropdown">
																			<span class="bigger-110">Action</span>

																			<i class="ace-icon fa fa-caret-down icon-on-right"></i>
																		</button>

																		<ul class="dropdown-menu dropdown-lighter dropdown-caret dropdown-125">
																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-mail-reply blue"></i>&nbsp; Reply
																				</a>
																			</li>

																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-mail-forward green"></i>&nbsp; Forward
																				</a>
																			</li>

																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-folder-open orange"></i>&nbsp; Archive
																				</a>
																			</li>

																			<li class="divider"></li>

																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-eye blue"></i>&nbsp; Mark as read
																				</a>
																			</li>

																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-eye-slash green"></i>&nbsp; Mark unread
																				</a>
																			</li>

																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-flag-o red"></i>&nbsp; Flag
																				</a>
																			</li>

																			<li class="divider"></li>

																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-trash-o red bigger-110"></i>&nbsp; Delete
																				</a>
																			</li>
																		</ul>
																	</div>

																	<div class="inline position-relative align-left">
																		<button type="button" class="btn-white btn-primary btn btn-xs dropdown-toggle" data-toggle="dropdown">
																			<i class="ace-icon fa fa-folder-o bigger-110 blue"></i>
																			<span class="bigger-110">Move to</span>

																			<i class="ace-icon fa fa-caret-down icon-on-right"></i>
																		</button>

																		<ul class="dropdown-menu dropdown-lighter dropdown-caret dropdown-125">
																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-stop pink2"></i>&nbsp; Tag#1
																				</a>
																			</li>

																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-stop blue"></i>&nbsp; Family
																				</a>
																			</li>

																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-stop green"></i>&nbsp; Friends
																				</a>
																			</li>

																			<li>
																				<a href="#">
																					<i class="ace-icon fa fa-stop grey"></i>&nbsp; Work
																				</a>
																			</li>
																		</ul>
																	</div>

																	<button type="button" class="btn btn-xs btn-white btn-primary">
																		<i class="ace-icon fa fa-trash-o bigger-125 orange"></i>
																		<span class="bigger-110">Delete</span>
																	</button>
																</div>
															</div>

															<div>
																<div class="messagebar-item-left">
																	<a href="#" class="btn-back-message-list">
																		<i class="ace-icon fa fa-arrow-left blue bigger-110 middle"></i>
																		<b class="bigger-110 middle">Back</b>
																	</a>
																</div>

																<div class="messagebar-item-right">
																	<i class="ace-icon fa fa-clock-o bigger-110 orange"></i>
																	<span class="grey">Today, 7:15 pm</span>
																</div>
															</div>
														</div>

														<div id="id-message-new-navbar" class="hide message-navbar clearfix">
															<div class="message-bar">
																<div class="message-toolbar">
																	<button type="button" class="btn btn-xs btn-white btn-primary">
																		<i class="ace-icon fa fa-floppy-o bigger-125"></i>
																		<span class="bigger-110">Save Draft</span>
																	</button>

																	<button type="button" class="btn btn-xs btn-white btn-primary">
																		<i class="ace-icon fa fa-times bigger-125 orange2"></i>
																		<span class="bigger-110">Discard</span>
																	</button>
																</div>
															</div>

															<div>
																<div class="messagebar-item-left">
																	<a href="#" class="btn-back-message-list">
																		<i class="ace-icon fa fa-arrow-left bigger-110 middle blue"></i>
																		<b class="middle bigger-110">Back</b>
																	</a>
																</div>

																<div class="messagebar-item-right">
																	<span class="inline btn-send-message">
																		<button type="button" class="btn btn-sm btn-primary no-border btn-white btn-round">
																			<span class="bigger-110">Send</span>

																			<i class="ace-icon fa fa-arrow-right icon-on-right"></i>
																		</button>
																	</span>
																</div>
															</div>
														</div>
	
															
															
														<!-- /section:pages/inbox.message-footer -->
													</div>
												</div>
											</div><!-- /.tab-content -->
										</div><!-- /.tabbable -->

										<!-- /section:pages/inbox -->
									</div><!-- /.col -->
								</div><!-- /.row -->

								<!--div que se refresca-->
								<div id="refresh"></div>
								<!-- PAGE CONTENT ENDS -->

								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div>
			</div><!-- /.main-content -->

			<div class="footer">
				<div class="footer-inner">
					<!-- #section:basics/footer -->
					<div class="footer-content">
						<span class="bigger-120">
							<span class="blue bolder">Ace</span>
							Application &copy; 2013-2014
						</span>

						&nbsp; &nbsp;
						<span class="action-buttons">
							<a href="#">
								<i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-rss-square orange bigger-150"></i>
							</a>
						</span>
					</div>

					<!-- /section:basics/footer -->
				</div>
			</div>

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='../assets/js/jquery.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='../assets/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='../assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
		</script>
		<script src="../assets/js/bootstrap.js"></script>

		<!-- page specific plugin scripts -->
		<script src="../assets/js/bootstrap-tag.js"></script>
		<script src="../assets/js/jquery.hotkeys.js"></script>
		<script src="../assets/js/bootstrap-wysiwyg.js"></script>

		<!-- ace scripts -->
		<script src="../assets/js/ace/elements.scroller.js"></script>
		<script src="../assets/js/ace/elements.colorpicker.js"></script>
		<script src="../assets/js/ace/elements.fileinput.js"></script>
		<script src="../assets/js/ace/elements.typeahead.js"></script>
		<script src="../assets/js/ace/elements.wysiwyg.js"></script>
		<script src="../assets/js/ace/elements.spinner.js"></script>
		<script src="../assets/js/ace/elements.treeview.js"></script>
		<script src="../assets/js/ace/elements.wizard.js"></script>
		<script src="../assets/js/ace/elements.aside.js"></script>
		<script src="../assets/js/ace/ace.js"></script>
		<script src="../assets/js/ace/ace.ajax-content.js"></script>
		<script src="../assets/js/ace/ace.touch-drag.js"></script>
		<script src="../assets/js/ace/ace.sidebar.js"></script>
		<script src="../assets/js/ace/ace.sidebar-scroll-1.js"></script>
		<script src="../assets/js/ace/ace.submenu-hover.js"></script>
		<script src="../assets/js/ace/ace.widget-box.js"></script>
		<script src="../assets/js/ace/ace.settings.js"></script>
		<script src="../assets/js/ace/ace.settings-rtl.js"></script>
		<script src="../assets/js/ace/ace.settings-skin.js"></script>
		<script src="../assets/js/ace/ace.widget-on-reload.js"></script>
		<script src="../assets/js/ace/ace.searchbox-autocomplete.js"></script>

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			jQuery(function($){
			
				//handling tabs and loading/displaying relevant messages and forms
				//not needed if using the alternative view, as described in docs
				$('#inbox-tabs a[data-toggle="tab"]').on('show.bs.tab', function (e) {
					var currentTab = $(e.target).data('target');
					if(currentTab == 'write') {
						Inbox.show_form();
					}
					else if(currentTab == 'inbox') {
						Inbox.show_list();
					}
				})
			
			
				
				//basic initializations
				$('.message-list .message-item input[type=checkbox]').removeAttr('checked');
				$('.message-list').on('click', '.message-item input[type=checkbox]' , function() {
					$(this).closest('.message-item').toggleClass('selected');
					if(this.checked) Inbox.display_bar(1);//display action toolbar when a message is selected
					else {
						Inbox.display_bar($('.message-list input[type=checkbox]:checked').length);
						//determine number of selected messages and display/hide action toolbar accordingly
					}		
				});
			
			
				//check/uncheck all messages
				$('#id-toggle-all').removeAttr('checked').on('click', function(){
					if(this.checked) {
						Inbox.select_all();
					} else Inbox.select_none();
				});
				
				//select all
				$('#id-select-message-all').on('click', function(e) {
					e.preventDefault();
					Inbox.select_all();
				});
				
				//select none
				$('#id-select-message-none').on('click', function(e) {
					e.preventDefault();
					Inbox.select_none();
				});
				
				//select read
				$('#id-select-message-read').on('click', function(e) {
					e.preventDefault();
					Inbox.select_read();
				});
			
				//select unread
				$('#id-select-message-unread').on('click', function(e) {
					e.preventDefault();
					Inbox.select_unread();
				});
			
				/////////
			
				//display first message in a new area
				$('.message-list .message-item:eq(0) .text').on('click', function() {
					//show the loading icon
					$('.message-container').append('<div class="message-loading-overlay"><i class="fa-spin ace-icon fa fa-spinner orange2 bigger-160"></i></div>');
					
					$('.message-inline-open').removeClass('message-inline-open').find('.message-content').remove();
			
					var message_list = $(this).closest('.message-list');
			
					$('#inbox-tabs a[href="#inbox"]').parent().removeClass('active');
					//some waiting
					setTimeout(function() {
			
						//hide everything that is after .message-list (which is either .message-content or .message-form)
						message_list.next().addClass('hide');
						$('.message-container').find('.message-loading-overlay').remove();
			
						//close and remove the inline opened message if any!
			
						//hide all navbars
						$('.message-navbar').addClass('hide');
						//now show the navbar for single message item
						$('#id-message-item-navbar').removeClass('hide');
			
						//hide all footers
						$('.message-footer').addClass('hide');
						//now show the alternative footer
						$('.message-footer-style2').removeClass('hide');
						
						
						//move .message-content next to .message-list and hide .message-list
						$('.message-content').removeClass('hide').insertAfter(message_list.addClass('hide'));
			
						//add scrollbars to .message-body
						$('.message-content .message-body').ace_scroll({
							size: 150,
							mouseWheelLock: true,
							styleClass: 'scroll-visible'
						});
			
					}, 500 + parseInt(Math.random() * 500));
				});
			
			
				//display second message right inside the message list
				$('.message-list .message-item:eq(1) .text').on('click', function(){
					var message = $(this).closest('.message-item');
			
					//if message is open, then close it
					if(message.hasClass('message-inline-open')) {
						message.removeClass('message-inline-open').find('.message-content').remove();
						return;
					}
			
					$('.message-container').append('<div class="message-loading-overlay"><i class="fa-spin ace-icon fa fa-spinner orange2 bigger-160"></i></div>');
					setTimeout(function() {
						$('.message-container').find('.message-loading-overlay').remove();
						message
							.addClass('message-inline-open')
							.append('<div class="message-content" />')
						var content = message.find('.message-content:last').html( $('#id-message-content').html() );
			
						//remove scrollbar elements
						content.find('.scroll-track').remove();
						content.find('.scroll-content').children().unwrap();
						
						content.find('.message-body').ace_scroll({
							size: 150,
							mouseWheelLock: true,
							styleClass: 'scroll-visible'
						});
				
					}, 500 + parseInt(Math.random() * 500));
					
				});
			
			
			
				//back to message list
				$('.btn-back-message-list').on('click', function(e) {
					
					e.preventDefault();
					$('#inbox-tabs a[href="#inbox"]').tab('show');
				});
			
			
			
				//hide message list and display new message form
				/**
				$('.btn-new-mail').on('click', function(e){
					e.preventDefault();
					Inbox.show_form();
				});
				*/
			
			
			
			
				var Inbox = {
					//displays a toolbar according to the number of selected messages
					display_bar : function (count) {
						if(count == 0) {
							$('#id-toggle-all').removeAttr('checked');
							$('#id-message-list-navbar .message-toolbar').addClass('hide');
							$('#id-message-list-navbar .message-infobar').removeClass('hide');
						}
						else {
							$('#id-message-list-navbar .message-infobar').addClass('hide');
							$('#id-message-list-navbar .message-toolbar').removeClass('hide');
						}
					}
					,
					select_all : function() {
						var count = 0;
						$('.message-item input[type=checkbox]').each(function(){
							this.checked = true;
							$(this).closest('.message-item').addClass('selected');
							count++;
						});
						
						$('#id-toggle-all').get(0).checked = true;
						
						Inbox.display_bar(count);
					}
					,
					select_none : function() {
						$('.message-item input[type=checkbox]').removeAttr('checked').closest('.message-item').removeClass('selected');
						$('#id-toggle-all').get(0).checked = false;
						
						Inbox.display_bar(0);
					}
					,
					select_read : function() {
						$('.message-unread input[type=checkbox]').removeAttr('checked').closest('.message-item').removeClass('selected');
						
						var count = 0;
						$('.message-item:not(.message-unread) input[type=checkbox]').each(function(){
							this.checked = true;
							$(this).closest('.message-item').addClass('selected');
							count++;
						});
						Inbox.display_bar(count);
					}
					,
					select_unread : function() {
						$('.message-item:not(.message-unread) input[type=checkbox]').removeAttr('checked').closest('.message-item').removeClass('selected');
						
						var count = 0;
						$('.message-unread input[type=checkbox]').each(function(){
							this.checked = true;
							$(this).closest('.message-item').addClass('selected');
							count++;
						});
						
						Inbox.display_bar(count);
					}
				}
			
				//show message list (back from writing mail or reading a message)
				Inbox.show_list = function() {
					$('.message-navbar').addClass('hide');
					$('#id-message-list-navbar').removeClass('hide');
			
					$('.message-footer').addClass('hide');
					$('.message-footer:not(.message-footer-style2)').removeClass('hide');
			
					$('.message-list').removeClass('hide').next().addClass('hide');
					//hide the message item / new message window and go back to list
				}
			
				//show write mail form
				Inbox.show_form = function() {
					if($('.message-form').is(':visible')) return;
					if(!form_initialized) {
						initialize_form();
					}
					
					
					var message = $('.message-list');
					$('.message-container').append('<div class="message-loading-overlay"><i class="fa-spin ace-icon fa fa-spinner orange2 bigger-160"></i></div>');
					
					setTimeout(function() {
						message.next().addClass('hide');
						
						$('.message-container').find('.message-loading-overlay').remove();
						
						$('.message-list').addClass('hide');
						$('.message-footer').addClass('hide');
						$('.message-form').removeClass('hide').insertAfter('.message-list');
						
						$('.message-navbar').addClass('hide');
						$('#id-message-new-navbar').removeClass('hide');
						
						
						//reset form??
						$('.message-form .wysiwyg-editor').empty();
					
						$('.message-form .ace-file-input').closest('.file-input-container:not(:first-child)').remove();
						$('.message-form input[type=file]').ace_file_input('reset_input');
						
						$('.message-form').get(0).reset();
						
					}, 300 + parseInt(Math.random() * 300));
				}
			
			
			
			
				var form_initialized = false;
				function initialize_form() {
					if(form_initialized) return;
					form_initialized = true;
					
					//intialize wysiwyg editor
					$('.message-form .wysiwyg-editor').ace_wysiwyg({
						toolbar:
						[
							'bold',
							'italic',
							'strikethrough',
							'underline',
							null,
							'justifyleft',
							'justifycenter',
							'justifyright',
							null,
							'createLink',
							'unlink',
							null,
							'undo',
							'redo'
						]
					}).prev().addClass('wysiwyg-style1');
			
			
			
					//file input
					$('.message-form input[type=file]').ace_file_input()
					.closest('.ace-file-input')
					.addClass('width-90 inline')
					.wrap('<div class="form-group file-input-container"><div class="col-sm-7"></div></div>');
			
					//Add Attachment
					//the button to add a new file input
					$('#id-add-attachment')
					.on('click', function(){
						var file = $('<input type="file" name="attachment[]" />').appendTo('#form-attachments');
						file.ace_file_input();
						
						file.closest('.ace-file-input')
						.addClass('width-90 inline')
						.wrap('<div class="form-group file-input-container"><div class="col-sm-7"></div></div>')
						.parent().append('<div class="action-buttons pull-right col-xs-1">\
							<a href="#" data-action="delete" class="middle">\
								<i class="ace-icon fa fa-trash-o red bigger-130 middle"></i>\
							</a>\
						</div>')
						.find('a[data-action=delete]').on('click', function(e){
							//the button that removes the newly inserted file input
							e.preventDefault();
							$(this).closest('.file-input-container').hide(300, function(){ $(this).remove() });
						});
					});
				}//initialize_form
			
				//turn the recipient field into a tag input field!
				/**	
				var tag_input = $('#form-field-recipient');
				try { 
					tag_input.tag({placeholder:tag_input.attr('placeholder')});
				} catch(e) {}
			
			
				//and add form reset functionality
				$('#id-message-form').on('reset', function(){
					$('.message-form .message-body').empty();
					
					$('.message-form .ace-file-input:not(:first-child)').remove();
					$('.message-form input[type=file]').ace_file_input('reset_input_ui');
			
					var val = tag_input.data('value');
					tag_input.parent().find('.tag').remove();
					$(val.split(',')).each(function(k,v){
						tag_input.before('<span class="tag">'+v+'<button class="close" type="button">&times;</button></span>');
					});
				});
				*/
			
			});
		</script>

		<!-- the following scripts are used in demo only for onpage help and you don't need them -->
		<link rel="stylesheet" href="../assets/css/ace.onpage-help.css" />
		<link rel="stylesheet" href="../docs/assets/js/themes/sunburst.css" />

		<script type="text/javascript"> ace.vars['base'] = '..'; </script>
		<script src="../assets/js/ace/elements.onpage-help.js"></script>
		<script src="../assets/js/ace/ace.onpage-help.js"></script>
		<script src="../docs/assets/js/rainbow.js"></script>
		<script src="../docs/assets/js/language/generic.js"></script>
		<script src="../docs/assets/js/language/html.js"></script>
		<script src="../docs/assets/js/language/css.js"></script>
		<script src="../docs/assets/js/language/javascript.js"></script>
	</body>
</html>
