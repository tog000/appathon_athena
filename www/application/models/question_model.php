<?php
class Question_model extends CI_Model {

	public function __construct()
	{
		$this->load->database();
	}

	public function get_question($id = FALSE)
	{
		if($id!==FALSE){

			$question = $this->db->get_where('question', array('id' => $id));
			$result = $question->result();
			if(count($result)>0){
				$question = $result[0];

				$query = $this->db->get_where('question_answer', array('id_question'=>$question->id));
				$answers = $query->result();
				
				$question->answers = array();
				$correct = -1;
				foreach($answers as $index=>$answer){
					if($answer->correct == 1){
						$correct = $index;
					}
					array_push($question->answers, $answer->text);
				}
				$question->correct = $correct;


				return $question;
			}
		}
		return NULL;
	}

	public function get_question_for_user($user_id = FALSE){
		
		if ($user_id !== FALSE)
		{

			$previously_answered = $this->db->get_where('user_question_answer', array('id_user'=>$user_id));
			$ids = [-1];
			foreach($previously_answered->result() as $index=>$object){
				array_push($ids, $object->id_question);
			}

			$this->db->select('id');
			$this->db->where_not_in('id',$ids);
			$query = $this->db->get('question');

			$unanswered = $query->result();

			// Choose one question at random
			if(count($unanswered)>0){
				$new_question = $unanswered[array_rand($unanswered)];
				return self::get_question($new_question->id);
			}else{
				$array = ["server_message"=>"No more questions to answer!"];
				return $array;
			}

		}

		return NULL;
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