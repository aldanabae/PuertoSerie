
								<?php 	
								$xml = simplexml_load_file('C:\Users\Fernix\Documents\variables.xml');
								$variables=$xml->variables;
								$mail=$xml->mail;
								//print_r($mail);
								
								foreach ($xml->mail as $mail){
									$dato=(string) $mail['id'];
								
									foreach ($mail->timestamp as $timestamp) {
										$fecha=(string) $timestamp['id'];
									
										$temp=(string) $timestamp->temperatura;
										$tension= (string) $timestamp->tension;
										$corriente= (string) $timestamp->corriente;
										$potencia= (string) $timestamp->potencia;
										$presion= (string) $timestamp->presion;
											
														
?>
								<div class=" message-content" id="id-message-content">
									<!-- #section:pages/inbox.message-header -->
									<div class="message-header clearfix">
										<div class="pull-left">
											

											<div class="space-4"></div>

											<i class="ace-icon fa fa-star orange2"></i>

											&nbsp;
											<img class="middle" alt="John's Avatar" src="../assets/avatars/avatar.png" width="32" />
											&nbsp;
											<a href="#" class="sender"><?php echo $dato; ?></a>

											&nbsp;
											<i class="ace-icon fa fa-clock-o bigger-110 orange middle"></i>
											<span class="time grey"><?php echo $fecha; ?></span>
										</div>

										
									</div>

									<!-- /section:pages/inbox.message-header -->
									<div class="hr hr-double"></div>

									<!-- #section:pages/inbox.message-body -->
									<div class="message-body">
										<p>
											Temperatura: <?php echo $temp; ?>°
										</p>

										<p>
											Tensión: <?php echo $tension; ?> V
										</p>

										<p>
											Corriente: <?php echo $corriente; ?> A
										</p>

										<p>
											Potencia: <?php echo $potencia; ?> W
										</p>

										<p>
											Presion: <?php echo $presion; ?> PSI
										</p> 

										
									</div>

									<!-- /section:pages/inbox.message-body -->
									<div class="hr hr-double"></div>

									

									<!-- /section:pages/inbox.message-attachment -->
								</div><!-- /.message-content -->
<?php } 								
}
									?>