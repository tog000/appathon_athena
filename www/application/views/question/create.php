<h2>Create a question</h2>

<?php echo validation_errors(); ?>

<?php echo form_open('question/create') ?>

	<label for="question">Question</label>
	
	<textarea name="question"></textarea><br />

	<label for="answers[]">Option 1</label>
	<input type="checkbox" name="correct" value="1"/>Correct? <input type="input" name="answers[]"/><br />
	<label for="answers[]">Option 2</label>
	<input type="checkbox" name="correct" value="2"/>Correct? <input type="input" name="answers[]"/><br />
	<label for="answers[]">Option 3</label>
	<input type="checkbox" name="correct" value="3"/>Correct? <input type="input" name="answers[]"/><br />
	<label for="answers[]">Option 4</label>
	<input type="checkbox" name="correct" value="4"/>Correct? <input type="input" name="answers[]"/><br />

	<input type="submit" name="submit" value="Create question" />

</form>