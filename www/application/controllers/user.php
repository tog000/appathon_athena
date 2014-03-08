<?php

class User extends CI_Controller{
	public function __construct()
	{
		parent::__construct();
		$this->load->model('user_model');
	}

	public function index()
	{

		$response = $this->user_model->get_user()[0];
		//print_r($response);
		echo json_encode($response);
	}

	public function view($id)
	{
		echo "view";
	}
	
	public function create()
	{
		$this->load->helper('form');
		$this->load->library('form_validation');

		$data['title'] = 'Create a question';
		$this->form_validation->set_rules('question', 'Question', 'required');

		if ($this->form_validation->run() === FALSE)
		{
			//$this->load->view('templates/header', $data);
			$this->load->view('question/create');
			//$this->load->view('templates/footer');

		}
		else
		{
			$this->question_model->set_question();
			$this->load->view('question/success');
		}
	}

}

?>