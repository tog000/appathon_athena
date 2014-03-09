<?php

class Achievement extends CI_Controller{
	public function __construct()
	{
		parent::__construct();
		$this->load->model('achievement_model');
	}

	public function get_achievements($id=NULL)
	{
		if($id!=NULL){
			$response = $this->achievement_model->get_achievements();
			echo json_encode($response);
		}
		return NULL;
	}
}

?>