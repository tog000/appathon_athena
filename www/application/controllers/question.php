<?php

class Question extends CI_Controller{
	public function __construct()
	{
		parent::__construct();
		$this->load->model('question_model');
	}

	public function index($id = NULL)
	{
		echo "nothing so see here...";
		return;
		$response = $this->question_model->get_question($id);
		if(count($response)>0){
			echo json_encode($response);
		}
	}

	public function question_for_user($user_id = NULL)
	{
		$response = $this->question_model->get_question_for_user($user_id);
		if(count($response)>0){
			echo json_encode($response);
		}
	}

	public function question_answered()
	{
		echo json_encode($this->question_model->add_answer());
	}

	public function question_answered_test($user_id)
	{
		print_r($this->question_model->compute_achievements($user_id));
	}

	public function get_achievements($id=NULL)
	{
		if($id!=NULL){
			$response = $this->question_model->get_achievements();
			echo json_encode($response);
		}
		return NULL;
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
			$this->load->view('header');
			$this->load->view('question/create');
			$this->load->view('footer');

		}
		else
		{
			$this->question_model->set_question();
			$this->load->view('question/success');
		}
	}

}

?>