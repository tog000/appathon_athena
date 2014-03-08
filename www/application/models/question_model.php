<?php
class Question_model extends CI_Model {

	public function __construct()
	{
		$this->load->database();
	}

	public function get_questions($id = FALSE)
	{
		if ($id === FALSE)
		{
			$query = $this->db->get('question');
			return $query->result_array();
		}

		$query = $this->db->get_where('question', array('id' => $id));
		return $query->row_array();
	}

	public function set_question()
	{

		$this->load->helper('url');

		$data = array(
			'question' => $this->input->post('question'),
			'experience' => $this->input->post('experience'),
			'image' => $this->input->post('image'),
			);

		$this->db->insert('question', $data);

		$insert_id = $this->db->insert_id();

		$answers = $this->input->post('answers');
		if (is_array($answers)) {
			foreach ($answers as $index => $answer) {
				
				$is_correct = $this->input->post('correct') == $index;

				$data = array(
					'id_question'=>$insert_id,
					'text'=>$answer,
					'correct'=>$is_correct,
					'time'=>time()
					);

				$this->db->insert('question_answer', $data);

			}
		}

		return $insert_id;

	}
}