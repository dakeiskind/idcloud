
	
		(function () {
				"use strict";
					
					
					
					/* ------------------------------------------------------------------------ */
					/* LOADER *///
					/* ------------------------------------------------------------------------  */

					 
					jQuery(window).load(function() { // makes sure the whole site is loaded
						jQuery('#preloader').delay(350).fadeOut('slow'); // will fade out the white DIV that covers the website.
						jQuery('body').delay(350).css({'overflow':'visible'});
					});
			
			


						/* ------------------------------------------------------------------------
					   TABS
					------------------------------------------------------------------------ */
					jQuery('#horizontalTab').easyResponsiveTabs({
						type: 'default', //Types: default, vertical, accordion
						width: 'auto', //auto or any width like 600px
						fit: true   // 100% fit in a container
					});




				
					/* ------------------------------------------------------------------------ 
					   ITEM COUNTER
					------------------------------------------------------------------------ */
				    var itemcount= 0;
		
					$('#pluss-item').on("click", function() { 
						itemcount++;
					$('#total-items').val(itemcount);
					});
				
					$('#less-item').on("click", function() { 
						itemcount--;
					$('#total-items').val(itemcount);
					});
					

					
					/* ------------------------------------------------------------------------ 
					   TOP TOGGLE SECTION
					------------------------------------------------------------------------ */
					jQuery("#toggle-btn").click(function(e) {
						jQuery("#top-sec-detail").slideToggle();
						jQuery("#toggle-btn i").toggleClass("fa-minus");
					});
					/* ------------------------------------------------------------------------
					   TEAM DESCRIPTION BOX
					------------------------------------------------------------------------ */
					jQuery(".team-des-btn").click(function(){			
						jQuery(this).parent('.team-member-description').find('div.team-desc').fadeToggle();
						jQuery(this).parent('.team-member-description').siblings('.team-member-description').find('div.team-desc').fadeOut();
						jQuery(this).parent('.team-member-description').siblings('.team-member-description').find('a.team-des-btn i').removeClass("fa-minus");
						jQuery(this).parent('.team-member-description').siblings('.team-member-description').find('a.team-des-btn').removeClass("active");
						jQuery(this).parent('.team-member-description').find('a.team-des-btn i').toggleClass("fa-minus");
						jQuery(this).parent('.team-member-description').find('a.team-des-btn').toggleClass("active");
					});
					
					
					/* ------------------------------------------------------------------------ */
					/* BACK TO TOP 
					/* ------------------------------------------------------------------------ */


						$(window).scroll(function(){
							if($(window).scrollTop() > 200){
								$("#back-to-top").addClass('to-top');
							} else{
								$("#back-to-top").removeClass('to-top');
							}
						});
						
						$('#back-to-top, .back-to-top, .navbar-brand').click(function() {
							  $('html, body').animate({ scrollTop:0 }, '800');
							  return false;
						});




		})(jQuery);

